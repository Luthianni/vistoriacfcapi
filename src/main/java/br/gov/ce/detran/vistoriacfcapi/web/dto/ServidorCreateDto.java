package br.gov.ce.detran.vistoriacfcapi.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ServidorCreateDto {
    
    @NotBlank
    @Size(min = 5, max = 100)
    private String nome;

    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

    @NotBlank
    @Size(min = 3, max = 7)
    private String matricula;

    @NotBlank
    @Email(message = "Formato de e-mail invalido", regexp ="^[a-zA-Z0-9.+]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    @Size(min = 11, max = 11)
    private String telefone;

}
