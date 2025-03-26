package br.gov.ce.detran.vistoriacfcapi.web.dto;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoResponseDto {

    private Long id;
    private Long cfcId;
    private String centroDeFormacao; // Do CFC
    private String cnpj; // Do CFC
    private String fantasia; // Do CFC
    private LocalDateTime dataHoraAgendamento;
    private Long usuarioId;
    private Long enderecoId;
    private LocalDateTime dataHoraPreferencia;
    private String tipoVistoria;
    private boolean primeiraVistoria;
    private String observacoes;

    public AgendamentoResponseDto(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.cfcId = agendamento.getCFC() != null ? agendamento.getCFC().getId() : null;
        this.centroDeFormacao = agendamento.getCFC() != null ? agendamento.getCFC().getCentroDeFormacao() : null;
        this.cnpj = agendamento.getCFC() != null ? agendamento.getCFC().getCnpj() : null;
        this.fantasia = agendamento.getCFC() != null ? agendamento.getCFC().getFantasia() : null;
        this.dataHoraAgendamento = agendamento.getDataHoraAgendamento();
        this.usuarioId = agendamento.getUsuario() != null ? agendamento.getUsuario().getId() : null;
        this.enderecoId = agendamento.getEndereco() != null ? agendamento.getEndereco().getId() : null;
        this.dataHoraPreferencia = agendamento.getDataHoraPreferencia();
        this.tipoVistoria = agendamento.getTipoVistoria() != null ? agendamento.getTipoVistoria().name() : null;
        this.primeiraVistoria = agendamento.isPrimeiraVistoria();
        this.observacoes = agendamento.getObservacoes();
    }
}