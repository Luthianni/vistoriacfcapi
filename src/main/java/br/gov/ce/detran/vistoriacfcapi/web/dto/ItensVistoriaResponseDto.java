package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ItensVistoriaResponseDto {
    
    private Long id;
    private boolean sala;
    private boolean banheiro;
    private boolean treinamento;
    private boolean portadorDeficiencia;
    private boolean suporte;
    private String observacao;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    private String criadoPor;
    private String modificadoPor;

    public void ItensVistoriaResponseDTO(
        Long id, 
        boolean sala,
        boolean banheiro,
        boolean treinamento,
        boolean portadorDeficiencia,
        boolean suporte, 
        String observacao,
        LocalDateTime dataCriacao, 
        LocalDateTime dataModificacao,
        String criadoPor, 
        String modificadoPor
        ) {

        this.id = id;
        this.sala = sala;
        this.banheiro = banheiro;
        this.treinamento = treinamento;
        this.portadorDeficiencia = portadorDeficiencia;
        this.suporte = suporte;
        this.observacao = observacao;
        this.dataCriacao = dataCriacao;
        this.dataModificacao = dataModificacao;
        this.criadoPor = criadoPor;
        this.modificadoPor = modificadoPor;
    }
}
