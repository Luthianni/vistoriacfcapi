package br.gov.ce.detran.vistoriacfcapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CentroFormacaoCondutorResponseDto {
    private Long id;
    private String nome;
    private String cnpj;
    
}
