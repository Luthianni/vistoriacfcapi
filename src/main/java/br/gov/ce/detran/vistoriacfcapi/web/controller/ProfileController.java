package br.gov.ce.detran.vistoriacfcapi.web.controller;

import java.util.HashMap;

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

import br.gov.ce.detran.vistoriacfcapi.entity.Profile;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.ProfileService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ProfileCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ProfileResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.ProfileMapper;
import br.gov.ce.detran.vistoriacfcapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Profile", description = "Contém todas as operações relativas ao recurso de um Profile")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {

        private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
        @Autowired
        private ProfileService profileService;
        @Autowired
        private UsuarioService usuarioService;

        @Operation(summary = "Criar um novo Profile", description = "Recurso para criar um novo profile vinculado a um usuário cadastrado. " +
                "Requisição exige uso de um bearer token.'",
                responses = {
                        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                                content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ProfileResponseDto.class))),
                        @ApiResponse(responseCode = "409", description = "CPF já possui cadastrado no sistema",
                                content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                        @ApiResponse(responseCode = "422", description = "Recurso não processados por falta de dados ou dados invalidos",
                                content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                        @ApiResponse(responseCode = "403", description = "Recurso não permiti ao perfil SERVIDOR",
                                content = @Content(mediaType = "Application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
                }
        )

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<ProfileResponseDto> create(@Valid @RequestBody ProfileCreateDto dto,
                                                         @AuthenticationPrincipal JwtUserDetails userDetails) {

                Profile profile = ProfileMapper.toProfile(dto);
                profile.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
                profile.setNome(dto.getNome());
                profile.setCpf(dto.getCpf());
                profile.setMatricula(dto.getMatricula());
                profile.setEmail(dto.getEmail());
                profile.setTelefone(dto.getTelefone());
                profile.setFoto(dto.getFoto());
                profile.setFoto(dto.getFotoBase64());
                profileService.salvar(profile);

                HashMap<Object, Object> result = new HashMap<>();
                result.put("nome", dto.getNome());
                result.put("matricula", dto.getEmail());
                result.put("email", dto.getEmail());
                result.put("cpf", dto.getCpf());
                result.put("foto", dto.getFoto());


                return ResponseEntity.status(HttpStatus.CREATED).body(ProfileMapper.toDto(profile));

        }

        @GetMapping("/{id}")
//@PreAuthorize("hasRole('ADMIN') OR (#id == authentication.principal.id)")
        public ResponseEntity<?> getById(@PathVariable Long id) {

                Profile profile = profileService.buscarPorId(id);
                if (profile != null) {
                        // Criando um objeto com a estrutura {result: {...}}
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("id", profile.getId());
                        result.put("nome", profile.getNome());
                        result.put("cpf", profile.getCpf());
                        result.put("matricula", profile.getMatricula());
                        result.put("email", profile.getEmail());
                        result.put("telefone", profile.getTelefone());
                        result.put("foto", profile.getFoto());

                        String fotoBase64 = profile.getFoto();
                        result.put("fotoBase64", profile.getFotoBase64());

                        // Criando o objeto final para retornar
                        HashMap<String, Object> response = new HashMap<>();
                        response.put("result", result);

                        // Retornando o objeto ResponseEntity com os dados do usuário na estrutura
                        // especificada
                        return ResponseEntity.ok(response);
                } else {
                        // Retorna 404 Not Found se o usuário não for encontrado
                        HashMap<String, Object> error = new HashMap<>();
                        error.put("error", "Usuário não encontrado");

                        return ResponseEntity.status(404).body(error);
                }
        }
}


