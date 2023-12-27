package br.gov.ce.detran.vistoriacfcapi.web.dto;

public class ItensVistoriaCreateDto {

    private boolean sala;
    private boolean banheiro;
    private boolean treinamento;
    private boolean portadorDeficiencia;
    private boolean suporte;
    private String observacao;

    public ItensVistoriaCreateDto(
        boolean sala,
        boolean banheiro,
        boolean treinamento,
        boolean portadorDeficiencia,
        boolean suporte,
        String observacao
        ) {

        this.sala = sala;
        this.banheiro = banheiro;
        this.treinamento = treinamento;
        this.portadorDeficiencia = portadorDeficiencia;
        this.suporte = suporte;
        this.observacao = observacao;

    }

}
