package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class VistoriaResponseDto {

    private Long id;
    private LocalDate dataAgendada;
    
}
