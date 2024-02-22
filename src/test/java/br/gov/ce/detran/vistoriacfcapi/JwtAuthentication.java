package br.gov.ce.detran.vistoriacfcapi;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.gov.ce.detran.vistoriacfcapi.jwt.JwtToken;
import br.gov.ce.detran.vistoriacfcapi.web.dto.UsuarioLoginDto;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto(null, username, password);
        
        try {
            String token = authenticateAndGetToken(client, usuarioLoginDto);
            return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao obter o token JWT após a autenticação.", e);
        }
    }

    private static String authenticateAndGetToken(WebTestClient client, UsuarioLoginDto usuarioLoginDto) {
        return client
            .post()
            .uri("/api/v1/auth")
            .bodyValue(usuarioLoginDto)
            .exchange()
            .expectStatus().isOk()
            .expectBody(JwtToken.class)
            .returnResult()
            .getResponseBody()
            .getToken();
}
}
