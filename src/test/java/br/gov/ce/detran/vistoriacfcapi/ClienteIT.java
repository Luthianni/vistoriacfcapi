package br.gov.ce.detran.vistoriacfcapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.gov.ce.detran.vistoriacfcapi.web.dto.ClienteCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ClienteResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClienteIT {

    @Autowired
    WebTestClient testClient;
    
    @Test
    public void criarCliente_ComDadosValidos_RetornarClienteComStatus201() {
        testClient
                .post()
                .uri("api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtAuthentication.getHeaderAuthorization(testClient, "tania@gmail.com", 123456))
                .bodyValue(new ClienteCreateDto("Tania Alves", "11284099091"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ClienteResponseDto.class)
                .returnResult().getResponseBody();
    }
}
