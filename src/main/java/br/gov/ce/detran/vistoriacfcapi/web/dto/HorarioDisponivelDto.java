package br.gov.ce.detran.vistoriacfcapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioDisponivelDto {
    private LocalDateTime horario;
    private boolean disponivel;
    private String observacao;

}
