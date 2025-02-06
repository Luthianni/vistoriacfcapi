package br.gov.ce.detran.vistoriacfcapi.entity;

import lombok.Getter;

@Getter
public enum StatusAgendamento {
    PENDENTE("Pendente"),
    CANCELADO("Cancelado"),
    REALIZADO("Realizado");

    private final String descricao;
    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }
}
