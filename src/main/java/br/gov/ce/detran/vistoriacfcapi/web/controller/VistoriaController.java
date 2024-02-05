package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.entity.Servidor;
import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.ServidorService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.service.VistoriaService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.EnderecoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.ServidorMapper;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.VistoriaMapper;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Vistoria", description = "Contem todas as operações relativas ao recurso da Vistória.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vistorias")
public class VistoriaController {

    
	private final VistoriaService vistoriaService; 
    private final UsuarioService usuarioService;
    private final ServidorService servidorService;
    
      @Operation(summary = "Criar uma nova vistoria", description = "Recurso para criar uma nova vistoria vinculado ao CFC e a um usuário cadastrado. " +
            "Requisição exige uso de um bearer token.'",
			responses = {
				@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", 
				content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = EnderecoResponseDto.class))),
				@ApiResponse(responseCode = "409", description = "Já possui uma vistoria cadastrada no sistema",
						content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
				@ApiResponse(responseCode = "422", description = "Recurso não processados por falta de dados ou dados invalidos",
						content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "Recurso não permiti ao perfil SERVIDOR",
                        content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
			}
	)

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VistoriaResponseDto> create(@Valid @RequestBody VistoriaCreateDto dto, 
    @AuthenticationPrincipal JwtUserDetails userDetails) {  
     	
        Servidor servidor = ServidorMapper.toServidor(dto);
    	Vistoria vistoria = VistoriaMapper.toVistoria(dto);    	
    	vistoria.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        vistoria.setDataAgendada(dto.getDataAgendada());
        vistoriaService.agendar(vistoria);

        HashMap<Object, Object> response = new HashMap<>();
        HashMap<Object, Object> result = new HashMap<>();

        result.put("Data Agendada", dto.getDataAgendada());
        response.put("result", result);
    	return ResponseEntity.status(HttpStatus.CREATED).body(VistoriaMapper.toDto(vistoria));    
    }
    
    

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (#id == authentication.principal.id)")
    public ResponseEntity<VistoriaResponseDto> getById(@PathVariable Long id) {
        Vistoria visto = vistoriaService.buscarPorId(id);
        return ResponseEntity.ok(VistoriaMapper.toDto(visto));
    }
    

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VistoriaResponseDto>> getAll() {
        List<Vistoria> visto = vistoriaService.buscarTodos();
        return ResponseEntity.ok(VistoriaMapper.toListDto(visto));
    }

}



