package br.gov.ce.detran.vistoriacfcapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class CFCResponseDto {
    private Long id;
    private String cnpj;
    private String centroDeFormacao;
    private String fantasia;  
    private String tipo;
    private String telefone;
    private String email;
    
}
