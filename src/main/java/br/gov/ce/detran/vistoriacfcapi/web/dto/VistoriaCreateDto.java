package br.gov.ce.detran.vistoriacfcapi.web.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VistoriaCreateDto {
  
    @NotBlank
    private LocalDateTime dataAgedada;

}
