package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDateTime;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponseDto {
    
    private Long id;
    private Long cfcId;
    private LocalDateTime dataHoraAgendamento;    
    private Long usuarioId;
    private Long enderecoId;
    private LocalDateTime dataHoraPreferencia;
    private String tipoVistoria;
    private boolean primeiraVistoria;
    private String observacoes;
    
    public AgendamentoResponseDto(Agendamento agendamento) {
        
    }

}