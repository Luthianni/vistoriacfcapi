package br.gov.ce.detran.vistoriacfcapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/centroFormacaoCondutores/centroFormacaoCondutores-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/centroFormacaoCondutores/centroFormacaoCondutores-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CentroFormacaoCondutorIT {

    @Autowired
    WebTestClient testClient;
    
    @Test
    public void criarCfc_ComDadosValidos_RetornarCfcComStatus201() {
        CFCResponseDto responseBody = testClient
                .post()
                .uri("api/v1/centroFormacaoCondutores")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "tania@gmail.com", "123456"))
                .bodyValue(new CFCCreateDto("Tania Alves", "11284099091"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CFCResponseDto.class)
                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Tania Alves");
                org.assertj.core.api.Assertions.assertThat(responseBody.getCnpj()).isEqualTo("11284099091");
    }
}
