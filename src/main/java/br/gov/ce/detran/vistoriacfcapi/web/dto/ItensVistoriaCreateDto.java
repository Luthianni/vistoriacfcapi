package br.gov.ce.detran.vistoriacfcapi.web.dto;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria.VistoriaStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItensVistoriaCreateDto {

    @NotNull(message = "A vistória da 'sala' não pode deixar em branco.")
    private boolean sala;

    @NotNull(message = "A vistória da 'banheiros' não pode deixar em branco.")
    private boolean banheiro;

    @NotNull(message = "A vistória da 'treinamento' não pode deixar em branco.")
    private boolean treinamento;

    @NotNull(message = "A vistória da 'portadorDeficiencia' não pode deixar em branco.")
    private boolean portadorDeficiencia;

    @NotNull(message = "A vistória da 'suporte' não pode deixar em branco.")
    private boolean suporte;

   
    private String observacao;  


}
