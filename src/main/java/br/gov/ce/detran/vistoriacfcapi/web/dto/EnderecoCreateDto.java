package br.gov.ce.detran.vistoriacfcapi.web.dto;

import br.gov.ce.detran.vistoriacfcapi.entity.TipoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EnderecoCreateDto {


    @NotBlank(message = "O campo 'logradouro' não pode estar em branco.")
    private String logradouro;

    @NotBlank(message = "O campo 'numero' não pode estar em branco.")
    private String numero;

    @NotBlank(message = "O campo 'bairro' não pode estar em branco.")
    private String bairro;

    @NotBlank(message = "O campo 'cidade' não pode estar em branco.")
    private String cidade;

    @NotBlank(message = "O campo 'cep' não pode estar em branco.")
    private String cep;
    
    @NotNull(message = "O campo 'tipoEndereco' não pode estar em branco.")
    private TipoEndereco tipoEndereco;
    
    private String complemento;

}
