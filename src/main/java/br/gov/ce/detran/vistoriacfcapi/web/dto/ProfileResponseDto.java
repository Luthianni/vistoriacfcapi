package br.gov.ce.detran.vistoriacfcapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String nome;
    private String cpf;
    private String matricula;
    private String email;
    private String telefone;
}
