package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ItensVistoriaCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ItensVistoriaResponseDto;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItensVistoriaMapper {
	

    public static ItensVistoria toItensVistoria(@Valid ItensVistoriaCreateDto dto) {
        return new ModelMapper().map(dto, ItensVistoria.class);
    }

    public static ItensVistoriaResponseDto toDto(ItensVistoria itensvistoria) {
        return new ModelMapper().map(itensvistoria, ItensVistoriaResponseDto.class);
    }

}
