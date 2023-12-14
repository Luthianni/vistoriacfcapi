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

import br.gov.ce.detran.vistoriacfcapi.entity.Servidor;
import br.gov.ce.detran.vistoriacfcapi.jwt.JwtUserDetails;
import br.gov.ce.detran.vistoriacfcapi.service.ServidorService;
import br.gov.ce.detran.vistoriacfcapi.service.UsuarioService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ServidorCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ServidorResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.ServidorMapper;
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


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServidorResponseDto> create(@RequestBody @Valid ServidorCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Servidor servidor = ServidorMapper.toServidor(dto);
        servidor.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        servidorService.salvar(servidor);
        return ResponseEntity.status(201).body(ServidorMapper.toDto(servidor));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('SERVIDOR') AND #id ==authentication.principal.id)")
    public ResponseEntity<ServidorResponseDto> getById(@PathVariable Long id) {
        Servidor serv = servidorService.buscarPorId(id);
        return ResponseEntity.ok(ServidorMapper.toDto(serv));

    }
    
}
