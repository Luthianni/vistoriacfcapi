package br.gov.ce.detran.vistoriacfcapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.gov.ce.detran.vistoriacfcapi.web.dto.CentroFormacaoCondutorCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CentroFormacaoCondutorResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CentroFormacaoCondutorIT {

    @Autowired
    WebTestClient testClient;
    
    @Test
    public void criarCliente_ComDadosValidos_RetornarClienteComStatus201() {
        CentroFormacaoCondutorResponseDto responseBody = testClient
                .post()
                .uri("api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "tania@gmail.com", "123456"))
                .bodyValue(new CentroFormacaoCondutorCreateDto("Tania Alves", "11284099091"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CentroFormacaoCondutorResponseDto.class)
                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Tania Alves");
                org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("11284099091");
    }
}
