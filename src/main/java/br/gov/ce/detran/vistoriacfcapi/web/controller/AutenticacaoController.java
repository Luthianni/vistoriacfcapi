package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.jwt.JwtToken;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetailsService;
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

    private final JwtUserDetailsService datailsService;
    private final AuthenticationManager authenticationManager;

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
                new UsernamePasswordAuthenticationToken(dto.getUsername(),  dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = datailsService.getTokenAuthenticated(dto.getUsername());
            
            
            HashMap<Object, Object> response = new HashMap<>();
            HashMap<Object, Object> result = new HashMap<>();
            
            result.put("username", dto.getUsername());
            result.put("token", token.getToken());            

            response.put("result", result);
            
            return ResponseEntity.ok(response);            
        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username '{}'", dto.getUsername());
        }
        return ResponseEntity
            .badRequest()
            .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Invalidas"));
    }
    
}
