package br.gov.ce.detran.vistoriacfcapi.web.controller;


import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.jwt.JwtToken;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetailsService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.UsuarioLoginDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.UsuarioResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Tag(name= "Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {
    
    
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private static final String secretKey = "0123456789-0123456789-0123456789";

    @Operation(summary = "Autenticar na API", description = "Recurso de autenticação na API",
			responses = {
				@ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso e retorno de um bearer token", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
				@ApiResponse(responseCode = "400", description = "Credenciais inválidas",
						content = @Content(mediaType = "Application/json", schema = @Schema(implementation = ErrorMessage.class))),
				@ApiResponse(responseCode = "422", description = "Campo(s) invalido(s)",
						content = @Content(mediaType = "Application/json", schema = @Schema(implementation = ErrorMessage.class))
				)
			}
	)

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
    
            // Obtenha o ID do usuário corretamente usando o serviço detailsService ou de onde estiver armazenado
            Long userId = detailsService.getUserIdByUsername(dto.getUsername());
    
            HashMap<Object, Object> response = new HashMap<>();
            HashMap<Object, Object> result = new HashMap<>();
            result.put("id", userId);  // Corrigido para usar userId ao invés de dto.getId()
            result.put("username", dto.getUsername());
            result.put("token", token.getToken());
    
            response.put("result", result);
    
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            log.warn("Credenciais inválidas do usuário '{}'", dto.getUsername());
            return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
        }
    }


    @SuppressWarnings("deprecation")
    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(HttpServletRequest request, @RequestHeader("Authorization") String tokenHeader) {
    try {
        log.info("Iniciando extração do nome de usuário do token...");

        if (StringUtils.isEmpty(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Cabeçalho de autorização ausente ou inválido"));
        }

        String token = StringUtils.substringAfter(tokenHeader, "Bearer ");
        String username = detailsService.extractUsernameFromToken(token, secretKey);

        log.info("Extração do nome de usuário concluída com sucesso: {}", username);

        if (StringUtils.isBlank(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED, "Usuário não autenticado"));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica se o usuário já está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof JwtUserDetails) {
                JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

                // Renova o token com um novo tempo de expiração
                JwtToken newToken = detailsService.generateToken(userDetails, 20 * 60 * 1000);

                HashMap<String, Object> result = new HashMap<>();
                result.put("id", userDetails.getId());
                result.put("username", userDetails.getUsername());
                result.put("token", newToken.getToken());

                HashMap<String, Object> response = new HashMap<>();
                response.put("result", result);

                return ResponseEntity.ok(response);
            } else {
                // Lidar com outros tipos de UserDetails, se aplicável
                return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Tipo de UserDetails desconhecido"));
            }
        }

        UserDetails userDetails = this.detailsService.loadUserByUsername(username);

        if (userDetails == null) {
            return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
        }

        if (!detailsService.isTokenValid(token, userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED, "Token inválido"));
        }

        Long userId = detailsService.getUserIdByUsername(username);
        JwtToken newToken = detailsService.generateToken(userDetails, 20 * 60 * 1000);

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", userId);
        result.put("username", userDetails.getUsername());
        result.put("token", newToken.getToken());

        HashMap<String, Object> response = new HashMap<>();
        response.put("result", result);

        return ResponseEntity.ok(response);
    } catch (TransactionException e) {
        log.error("Erro ao extrair o nome de usuário do token", e);
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Token inválido"));
    } catch (Exception e) {
        log.error("Erro desconhecido", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar a solicitação"));
    }
}
}
    