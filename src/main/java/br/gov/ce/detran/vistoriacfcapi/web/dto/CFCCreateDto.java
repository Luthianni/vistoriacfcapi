package br.gov.ce.detran.vistoriacfcapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor @AllArgsConstructor
public class CFCCreateDto {
    // Getters e setters
    @NotNull(message = "O CNPJ é obrigatório")
    private String cnpj;

    @NotNull(message = "O centro de formação é obrigatório")
    private String centroDeFormacao;

    @NotNull(message = "O nome fantasia é obrigatório")
    private String fantasia;

    @NotNull(message = "O tipo é obrigatório")
    private String tipo;

    private String telefone; // Alterado para String simples, já que ReceitaWS retorna assim

    @NotNull(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "O CEP é obrigatório")
    private String cep;

    @NotNull(message = "O endereço é obrigatório")
    private String endereco; // Alterado de "logradouro" para "endereco"

    @NotNull(message = "O número é obrigatório")
    private String numero;

    private String complemento;

    @NotNull(message = "O bairro é obrigatório")
    private String bairro;

    @NotNull(message = "A cidade é obrigatória")
    private String cidade;

    @NotNull(message = "O estado é obrigatório")
    private String estado; // Alterado de "uf" para "estado"

    private String data;
}