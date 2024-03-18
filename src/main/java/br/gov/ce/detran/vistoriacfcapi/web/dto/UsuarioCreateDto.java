package br.gov.ce.detran.vistoriacfcapi.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@ToString
public class UsuarioCreateDto {

    @NotBlank
    @Email(message = "Formato de e-mail invalido", regexp ="^[a-zA-Z0-9.+]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
    
    @NotBlank
    @Size(min = 5, max = 200)
    private String nome;

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    @Size(min = 02, max = 6)
    private String matricula;

    @NotBlank
    @Email(message = "Formato de e-mail invalido", regexp ="^[a-zA-Z0-9.+]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$")
    private String email;

    @NotBlank
    @Size(min = 8, max = 9)
    private String telefone;

}
