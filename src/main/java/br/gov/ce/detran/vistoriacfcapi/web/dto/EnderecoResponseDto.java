package br.gov.ce.detran.vistoriacfcapi.web.dto;

import br.gov.ce.detran.vistoriacfcapi.entity.TipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EnderecoResponseDto {

    private Long id;
    private String cidade;
    private String bairro;
    private String estado;
    private String endereco;
    private String cep;
    private String numero;
    private String complemento;
    private TipoEndereco tipoEndereco;

}
