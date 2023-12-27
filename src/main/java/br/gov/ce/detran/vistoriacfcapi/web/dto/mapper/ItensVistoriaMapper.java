package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ItensVistoriaResponseDto;



public class ItensVistoriaMapper {
	

    public static ItensVistoria toItensVistoria(ItensVistoriaMapper createDto) {
        return new ModelMapper().map(createDto, ItensVistoria.class);
    }

    public static ItensVistoriaResponseDto toDto(ItensVistoria itensvistoria) {
        return new ModelMapper().map(itensvistoria, ItensVistoriaResponseDto.class);
    }

}
