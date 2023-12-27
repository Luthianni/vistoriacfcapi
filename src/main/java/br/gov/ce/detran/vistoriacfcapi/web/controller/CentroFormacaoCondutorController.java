package br.gov.ce.detran.vistoriacfcapi.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.entity.CentroFormacaoCondutor;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.CentroFormacaoCondutorService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CentroFormacaoCondutorCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CentroFormacaoCondutorResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.CentroFormacaoCondutorMapper;
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
@RequestMapping("api/v1/centroFormacaoCondutores")
public class CentroFormacaoCondutorController {

    private final CentroFormacaoCondutorService centroFormacaoCondutorService;    
    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo Centro de Formação de Condutores", description = "Recurso para criar um novo cfc vinculado a um usuário cadastrado. " +
            "Requisição exige uso de um bearer token.'",
			responses = {
				@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", 
				content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = CentroFormacaoCondutorResponseDto.class))),
				@ApiResponse(responseCode = "409", description = "CNPJ já possui cadastrado no sistema",
						content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
				@ApiResponse(responseCode = "422", description = "Recurso não processados por falta de dados ou dados invalidos",
						content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "Recurso não permiti ao perfil SERVIDOR",
                        content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CentroFormacaoCondutorResponseDto> create(@RequestBody @Valid CentroFormacaoCondutorCreateDto dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails) {
        CentroFormacaoCondutor centroFormacaoCondutor = CentroFormacaoCondutorMapper.toCentroFormacaoCondutor(dto);
        centroFormacaoCondutor.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        centroFormacaoCondutorService.salvar(centroFormacaoCondutor);
        return ResponseEntity.status(201).body(CentroFormacaoCondutorMapper.toDto(centroFormacaoCondutor));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CentroFormacaoCondutorResponseDto> getById(@PathVariable Long id) {
        CentroFormacaoCondutor centroFormacaoCondutor = centroFormacaoCondutorService.buscarPorId(id);
        return ResponseEntity.ok(CentroFormacaoCondutorMapper.toDto(centroFormacaoCondutor));
    }
}
