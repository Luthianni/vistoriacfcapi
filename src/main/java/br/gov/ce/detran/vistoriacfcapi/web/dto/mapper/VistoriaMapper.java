package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ItensVistoriaCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.VistoriaResponseDto;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VistoriaMapper {

    public static Vistoria toVistoria(VistoriaCreateDto dto) {
        return new ModelMapper().map(dto, Vistoria.class);
    }

    public static VistoriaResponseDto toDto(Vistoria vistoria) {
        return new ModelMapper().map(vistoria, VistoriaResponseDto.class);
    }

    public static Vistoria toVistoria(@Valid ItensVistoriaCreateDto dto) {        
        throw new UnsupportedOperationException("Unimplemented method 'toVistoria'");
    }

    public static List<VistoriaResponseDto> toListDto(List<Vistoria> vistorias) {
        return vistorias.stream().map(visto -> toDto(visto)).collect(Collectors.toList());
    }

        
}
