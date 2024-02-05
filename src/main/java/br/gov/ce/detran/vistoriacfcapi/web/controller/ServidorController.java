package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.entity.Servidor;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.ServidorService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.EnderecoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ServidorCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ServidorResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.ServidorMapper;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Servidores", description = "Contém todas as operações relativas ao recurso de um servidor")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/servidores")
public class ServidorController {

    private final ServidorService servidorService;
    private final UsuarioService usuarioService;

      @Operation(summary = "Criar um novo servidor", description = "Recurso para criar um novo servidor." +
            "Requisição exige uso de um bearer token.'",
			responses = {
				@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", 
				content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = EnderecoResponseDto.class))),
				@ApiResponse(responseCode = "409", description = "Servidor já possui cadastrado no sistema",
						content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
				@ApiResponse(responseCode = "422", description = "Recurso não processados por falta de dados ou dados invalidos",
						content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "Recurso não permiti ao perfil SERVIDOR",
                        content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServidorResponseDto> create(@RequestBody @Valid ServidorCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Servidor servidor = ServidorMapper.toServidor(dto);
        servidor.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        servidorService.salvar(servidor);

        HashMap<Object, Object> response = new HashMap<>();
        HashMap<Object, Object> result = new HashMap<>();

        result.put("nome", dto.getNome());
        result.put("cpf", dto.getCpf());
        result.put("email", dto.getEmail());
        result.put("matricula", dto.getMatricula());

        response.put("result", result);

        return ResponseEntity.status(201).body(ServidorMapper.toDto(servidor));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServidorResponseDto> getById(@PathVariable Long id) {
        Servidor serv = servidorService.buscarPorId(id);
        return ResponseEntity.ok(ServidorMapper.toDto(serv));

    }
    
    
}
