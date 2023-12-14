package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VistoriaResponseDto {

    private Long id;
    private LocalDateTime dataAgendada;
    
}
