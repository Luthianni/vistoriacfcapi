package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VistoriaResponseDto {

    private Long id;
    private LocalDateTime dataAgendada;
    
}
