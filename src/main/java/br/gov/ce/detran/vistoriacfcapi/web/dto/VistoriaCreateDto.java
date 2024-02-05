package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VistoriaCreateDto {
  
	@NotNull(message = "O campo 'dataAgendada' não pode estar em branco.")
	@DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataAgendada;

}
