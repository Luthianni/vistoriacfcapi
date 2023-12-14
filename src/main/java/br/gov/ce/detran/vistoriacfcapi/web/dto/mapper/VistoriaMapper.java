package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaResponseDto;

public class VistoriaMapper {

    public static Vistoria toVistoria(VistoriaCreateDto createDto) {
        return new ModelMapper().map(createDto, Vistoria.class);
    }

    public static VistoriaResponseDto toDto(Vistoria vistoria) {
        return new ModelMapper().map(vistoria, VistoriaResponseDto.class);
    }
    
}
