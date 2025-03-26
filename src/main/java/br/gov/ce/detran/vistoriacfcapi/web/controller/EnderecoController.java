package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
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
import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.CFCService;
import br.gov.ce.detran.vistoriacfcapi.service.EnderecoService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.EnderecoCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.EnderecoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.EnderecoMapper;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Slf4j
@Tag(name = "Endereco", description = "Contem todas as operações relativas ao recurso de Endereço.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Operation(summary = "Criar um novo endereço", description = "Recurso para criar um novo endereço. " +
            "Requisição exige uso de um bearer token.'",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = EnderecoResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Endereço já possui cadastrado no sistema",
                            content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processados por falta de dados ou dados inválidos",
                            content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil SERVIDOR",
                            content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnderecoResponseDto> create(@RequestBody @Valid EnderecoCreateDto dto,
                                                      @AuthenticationPrincipal JwtUserDetails userDetails) {
        log.info("Recebendo solicitação para criar endereço: {}", dto);

        // Mapeia o DTO para a entidade Endereco
        Endereco endereco = EnderecoMapper.toEndereco(dto);

        // Salva o endereço
        Endereco savedEndereco = enderecoService.salvar(endereco);

        // Retorna o DTO mapeado
        return ResponseEntity.status(201).body(EnderecoMapper.toDto(savedEndereco));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> getById(@PathVariable Long id) {
        Endereco endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(EnderecoMapper.toDto(endereco));
    }
}
