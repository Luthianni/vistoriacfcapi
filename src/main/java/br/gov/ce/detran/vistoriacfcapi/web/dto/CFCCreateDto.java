package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.util.List;

import br.gov.ce.detran.vistoriacfcapi.web.controller.CFCController;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import br.gov.ce.detran.vistoriacfcapi.web.utils.TelefoneUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CFCCreateDto {
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

    // Getters e setters
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getCentroDeFormacao() { return centroDeFormacao; }
    public void setCentroDeFormacao(String centroDeFormacao) { this.centroDeFormacao = centroDeFormacao; }
    public String getFantasia() { return fantasia; }
    public void setFantasia(String fantasia) { this.fantasia = fantasia; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}