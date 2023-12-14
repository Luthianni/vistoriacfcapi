package br.gov.ce.detran.vistoriacfcapi.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;
import br.gov.ce.detran.vistoriacfcapi.service.VistoriaService;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.mapper.VistoriaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vistorias")
public class VistoriaController {

    private final VistoriaService vistoriaService;        

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VistoriaResponseDto> create(@Valid @RequestBody VistoriaCreateDto createDto) {
        Vistoria visto = vistoriaService.agendar(VistoriaMapper.toVistoria(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(VistoriaMapper.toDto(visto));
    }

}


