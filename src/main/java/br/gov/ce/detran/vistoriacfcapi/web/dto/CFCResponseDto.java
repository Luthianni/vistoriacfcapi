package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CFCResponseDto {
    private Long id;
    private String cnpj;
    private String centroDeFormacao;
    private String fantasia;
    private String tipo;
    private String telefone;
    private String email;
    private LocalDateTime data; // Compatível com dataCriacao e dataModificacao
    private EnderecoResponseDto endereco;
    private UsuarioResponseDto usuario;
}