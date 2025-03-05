package br.gov.ce.detran.vistoriacfcapi.entity;

import lombok.Getter;

@Getter
public enum TipoVistoria {
    INICIAL("Vistoria Inicial"),
    RENOVACAO("Renovação"),
    MUDANCA_ENDERECO("Mudança de Endereço"),
    COMPARTILHAMENTO("Compartilhamento");

    private final String descricao;

    TipoVistoria(String descricao) {
        this.descricao = descricao;
    }

    public String toUpperCase() {
         throw new UnsupportedOperationException("Unimplemented method 'toUpperCase'");
    }
}
