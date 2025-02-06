package br.gov.ce.detran.vistoriacfcapi.web.dto;

import br.gov.ce.detran.vistoriacfcapi.entity.StatusAgendamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoFiltroDto {
    private Long cfcId;
    private StatusAgendamento status;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
