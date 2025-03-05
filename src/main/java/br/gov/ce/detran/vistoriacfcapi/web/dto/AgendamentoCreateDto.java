package br.gov.ce.detran.vistoriacfcapi.web.dto;


import java.time.LocalDateTime;

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
public class AgendamentoCreateDto {
    private Long cfcId;
    private LocalDateTime dataHoraAgendamento;
    
    private Long usuarioId;
    private Long enderecoId;
    private LocalDateTime dataHoraPreferencia;
    private String tipoVistoria;
    private boolean primeiraVistoria;
    private String observacoes;

      // Fields for CFC data
      private String centroDeFormacao;
      private String fantasia;
      private String cnpj;
      private String tipo;
      private String telefone;
      private String email;
  
      // Fields for Endereco data
      private String endereco;
      private String numero;
      private String bairro;
      private String cidade;
      private String estado;
      private String cep;
   
    
}


