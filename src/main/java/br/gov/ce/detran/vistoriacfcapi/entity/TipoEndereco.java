package br.gov.ce.detran.vistoriacfcapi.entity;

public enum TipoEndereco {

    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial"),
    OUTRO("Outro");
    
    private final String descricao;

    TipoEndereco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
