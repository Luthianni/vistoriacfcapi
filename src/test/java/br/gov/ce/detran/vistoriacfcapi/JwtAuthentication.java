package br.gov.ce.detran.vistoriacfcapi;


import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.gov.ce.detran.vistoriacfcapi.jwt.JwtToken;
import br.gov.ce.detran.vistoriacfcapi.web.dto.UsuarioLoginDto;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = client
            .post()
            .uri("/api/v1/auth")
            .bodyValue(new UsuarioLoginDto(username, password))
            .exchange()
            .expectStatus().isOk()
            .expectBody(JwtToken.class)
            .returnResult().getResponseBody().getToken();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer" + token);
    }
    
}
