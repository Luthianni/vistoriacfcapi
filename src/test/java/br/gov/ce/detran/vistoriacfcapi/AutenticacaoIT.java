package br.gov.ce.detran.vistoriacfcapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.gov.ce.detran.vistoriacfcapi.jwt.JwtToken;
import br.gov.ce.detran.vistoriacfcapi.web.dto.UsuarioLoginDto;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AutenticacaoIT {

    @Autowired
    WebTestClient testClient;

  @Test
    public void autenticar_ComCredenciaisValidas_RetornarTokenComStatus200() {
        JwtToken responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null, "luthianni@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @SuppressWarnings("null")
@Test
    public void autenticar_ComCredenciaisInvalidas_RetornarErrorMessageStatus400() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null, "invalido@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null, "luthianni@gmail.com", "000000"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void autenticar_ComUsernameInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null ,"", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                if (responseBody != null) {
                    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
                }

        responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null, "@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                if (responseBody != null) {
                    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
                }
    }

    @Test
    public void autenticar_ComPasswordInvalido_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null, "luthianni@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                if (responseBody != null) {
                    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
                }

        responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null, "luthianni@gmail.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                if (responseBody != null) {
                    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
                }

        responseBody = testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto(null, "luthianni@gmail.com", "123456789"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();
                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                if (responseBody != null) {
                    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
                }
        }


}
