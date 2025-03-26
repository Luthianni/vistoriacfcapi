package br.gov.ce.detran.vistoriacfcapi.web.dto;

import br.gov.ce.detran.vistoriacfcapi.entity.TipoVistoria;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AgendamentoCreateDto {

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @NotNull(message = "Data e hora do agendamento são obrigatórias")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dataHoraAgendamento;

    @NotNull(message = "Data e hora do agendamento Preferencial")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime DataHoraPreferencia;

    @NotNull(message = "CFC ID é obrigatório")
    private Long cfcId;

    @NotNull(message = "Usuário ID é obrigatório")
    private Long usuarioId;

    @NotNull(message = "Tipo de vistoria é obrigatório")
    private TipoVistoria tipoVistoria;

    private String observacoes;

    @NotNull(message = "Primeira vistoria é obrigatória")
    private Boolean primeiraVistoria;
}