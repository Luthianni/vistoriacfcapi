package br.gov.ce.detran.vistoriacfcapi.web.dto;

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
public class UsuarioLoginDto {

    private Long id;

    @NotBlank
    @Email(message = "Formato de e-mail invalido", regexp ="^[a-zA-Z0-9.+]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
    
}
