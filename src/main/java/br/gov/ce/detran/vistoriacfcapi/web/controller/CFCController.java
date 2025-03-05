package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.repository.EnderecoRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.CFCService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.CFCMapper;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "CFC", description = "Contém todas as operações relativas ao recurso de um Centro de Formacao de Condutores")
@RequiredArgsConstructor
@RestController
@RequestMapping("/centroFormacaoCondutores")
public class CFCController {

    private static final Logger logger = LoggerFactory.getLogger(CFCController.class);
    @Autowired
    private CFCService cFCService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Operation(summary = "Criar um novo Centro de Formação de Condutores", description = "Recurso para criar um novo cfc vinculado a um usuário cadastrado. " +
            "Requisição exige uso de um bearer token.'",
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", 
                content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = CFCResponseDto.class))),
                @ApiResponse(responseCode = "409", description = "CNPJ já possui cadastrado no sistema",
                        content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Recurso não processados por falta de dados ou dados invalidos",
                        content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "Recurso não permiti ao perfil SERVIDOR",
                        content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @PostMapping
    public ResponseEntity<CFCResponseDto> create(@RequestBody @Valid CFCCreateDto dto,
                                                 @AuthenticationPrincipal JwtUserDetails userDetails) {
        CFC cfc = CFCMapper.toCFC(dto);
        cfc.setUsuario(usuarioService.buscarPorId(userDetails.getId()));

        // Cria e preenche o objeto Endereco
        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setEndereco(dto.getEndereco());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setEstado(dto.getEstado());
        // Usa o mesmo valor de "estado" para "uf"

        // Salva o Endereco
        Endereco savedEndereco = enderecoRepository.save(endereco);
        cfc.setEndereco(savedEndereco);

        // Salva o CFC
        CFC savedCfc = cFCService.salvar(cfc);

        // Prepara o resultado para o log
        HashMap<Object, Object> result = new HashMap<>();
        HashMap<Object, Object> enderecoMap = new HashMap<>();

        List<String> telefones = new ArrayList<>();
        if (dto.getTelefone() != null && !dto.getTelefone().isEmpty()) {
            telefones = Arrays.asList(dto.getTelefone().split(" / "));
        }

        result.put("cnpj", dto.getCnpj());
        result.put("centroDeFormacao", dto.getCentroDeFormacao());
        result.put("fantasia", dto.getFantasia());
        result.put("tipo", dto.getTipo());
        result.put("telefone", telefones);
        result.put("email", dto.getEmail());

        enderecoMap.put("cep", dto.getCep());
        enderecoMap.put("endereco", dto.getEndereco());
        enderecoMap.put("numero", dto.getNumero());
        enderecoMap.put("complemento", dto.getComplemento());
        enderecoMap.put("bairro", dto.getBairro());
        enderecoMap.put("cidade", dto.getCidade());
        enderecoMap.put("estado", dto.getEstado());

        result.put("endereco", enderecoMap);
        result.put("data", dto.getData());

        logger.info("CFC created with details: {}", result);

        return ResponseEntity.status(HttpStatus.CREATED).body(CFCMapper.toDto(savedCfc));
    }

    @Operation(summary = "Buscar um Centro de Formação de Condutores por ID", description = "Recurso para buscar um CFC com base no ID. " +
            "Requisição exige uso de um bearer token e role ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = CFCResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Centro de Formação não encontrado",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CFCResponseDto> getById(@PathVariable Long id) {
        CFC cFC = cFCService.buscarPorId(id);
        if (cFC == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(CFCMapper.toDto(cFC));
    }

    @Operation(summary = "Buscar um Centro de Formação de Condutores por CNPJ", description = "Recurso para buscar um CFC com base no CNPJ. " +
            "Requisição exige uso de um bearer token, se necessário.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso encontrado com sucesso",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = CFCResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Centro de Formação não encontrado",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/cnpj/{cnpj}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Alterado para aceitar ambas as roles
    public ResponseEntity<CFCResponseDto> getByCnpj(@PathVariable String cnpj) {
        CFC cfc = cFCService.buscarPorCnpj(cnpj);
        if (cfc == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(CFCMapper.toDto(cfc));
    }
    @GetMapping
    public ResponseEntity<List<CFCResponseDto>> getAll() {
        List<CFC> cfcs = cFCService.buscarTodos();
        return ResponseEntity.ok(CFCMapper.toListDto(cfcs));
    }
    public class TelefoneDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String telefone = p.getText();
            return Arrays.asList(telefone.split(" / ")); // Divide a string em lista de telefones
        }
    }

}