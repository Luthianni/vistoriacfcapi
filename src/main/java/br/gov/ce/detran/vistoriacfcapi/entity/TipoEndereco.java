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

    // Método para buscar TipoEndereco por descrição
    public static TipoEndereco fromDescricao(String descricao) {
        for (TipoEndereco tipo : TipoEndereco.values()) {
            if (tipo.getDescricao().equalsIgnoreCase(descricao)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de endereço inválido: " + descricao);
    }
}