package br.gov.ce.detran.vistoriacfcapi.web.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CFCCreateDto {
    
    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    @Size(min = 14, max = 14)
    @CNPJ
    private String cnpj;

   
    
}
