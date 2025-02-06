package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;
import java.util.List;

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
@RequestMapping("api/v1/centroFormacaoCondutores")
public class CFCController {

    private final CFCService cFCService;    
    private final UsuarioService usuarioService;

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
                        content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CFCResponseDto> create(@RequestBody @Valid CFCCreateDto dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails) {
        CFC cFC = CFCMapper.toCFC(dto);
        cFC.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        cFCService.salvar(cFC);
        HashMap<Object, Object> response = new HashMap<>();
        HashMap<Object, Object> result = new HashMap<>();
                
        result.put("cnpj", dto.getCnpj());   
        result.put("centroDeFormacao", dto.getCentroDeFormacao());  
        result.put("fantasia", dto.getFantasia());
        result.put("tipo", dto.getTipo());
        result.put("telefone", dto.getTelefone());
        result.put("email", dto.getEmail());               

        response.put("result", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(CFCMapper.toDto(cFC));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CFCResponseDto> getById(@PathVariable Long id) {
        CFC cFC = cFCService.buscarPorId(id);
        return ResponseEntity.ok(CFCMapper.toDto(cFC));
    }

    @GetMapping
    public ResponseEntity<List<CFCResponseDto>> getAll() {
        List<CFC> cfcs = cFCService.buscarTodos();
        return ResponseEntity.ok(CFCMapper.toListDto(cfcs));
    }
}
