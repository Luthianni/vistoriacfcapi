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
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUtils;
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

@Tag(name= "Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {
    
    
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;
    private static final String SECRET_KEY = JwtUtils.SECRET_KEY;

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
            result.put("status", HttpStatus.OK.value());
            result.put("message", "Autenticado");
            result.put("path", request.getRequestURI());
    
            response.put("result", result);
    
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            log.warn("Credenciais inválidas do usuário '{}'", dto.getUsername());

            HashMap<Object, Object> errorResponse = new HashMap<>();
            HashMap<Object, Object> error = new HashMap<>();
            error.put("status", HttpStatus.BAD_REQUEST.value());
            error.put("error", "Bad Request");
            error.put("message", "Credenciais Inválidas");
            error.put("path", request.getRequestURI());

            errorResponse.put("error", error);

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(HttpServletRequest request, @RequestHeader("Authorization") String tokenHeader) {
    try {
        log.info("Iniciando extração do nome de usuário do token...");

        if (StringUtils.isEmpty(tokenHeader) || !tokenHeader.startsWith("Bearer ")) {
            
            HashMap<String, Object> error = new HashMap<>();
            error.put("error", "Cabeçalho de autorização ausente ou inválido");

            HashMap<String, Object> response = new HashMap<>();
            response.put("error", error);

            return ResponseEntity.status(400).body(error);
            
        }

        String token = StringUtils.substringAfter(tokenHeader, "Bearer ");
        String username = detailsService.extractUsernameFromToken(token, SECRET_KEY);

        log.info("Extração do nome de usuário concluída com sucesso: {}", username);

        if (StringUtils.isBlank(username)) {

            HashMap<String, Object> error = new HashMap<>();
            error.put("error", "Usuário não autenticado");

            HashMap<String, Object> response = new HashMap<>();
            response.put("error", error);

            return ResponseEntity.status(401).body(error);
            
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
                result.put("status", HttpStatus.ACCEPTED.value());
                result.put("message", "Validado");
                result.put("path", request.getRequestURI());

                HashMap<String, Object> response = new HashMap<>();
                response.put("result", result);

                return ResponseEntity.ok(response);
            } else {

                HashMap<String, Object> error = new HashMap<>();
                error.put("error", "Tipo de UserDetails desconhecido");

                HashMap<String, Object> response = new HashMap<>();
                response.put("error", error);
                // Lidar com outros tipos de UserDetails, se aplicável
                return ResponseEntity.status(400).body(error);
            }
        }

        UserDetails userDetails = this.detailsService.loadUserByUsername(username);

        if (userDetails == null) {

            HashMap<String, Object> error = new HashMap<>();
                error.put("error", "Usuário não encontrado");

                HashMap<String, Object> response = new HashMap<>();
                response.put("error", error);
                
            return ResponseEntity.status(400).body(error);
            
        }

        if (!detailsService.isTokenValid(token, userDetails)) {

            HashMap<String, Object> error = new HashMap<>();
                error.put("error", "Token inválido");

                HashMap<String, Object> response = new HashMap<>();
                response.put("error", error);
                
            return ResponseEntity.status(401).body(error);
            
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
        HashMap<String, Object> error = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", error);
        return ResponseEntity.status(400).body(error);
    } catch (Exception e) {
        log.error("Erro desconhecido", e);
        HashMap<String, Object> error = new HashMap<>();
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", error);
        error.put("error", "Erro ao processar a solicitação");

        return ResponseEntity.status(500).body(error);
        
    }
    }
}
    