package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CFCMapper {
    

    public static CFC toCFC(CFCCreateDto dto) {
        return new ModelMapper().map(dto, CFC.class);
    }

    public static CFCResponseDto toDto(CFC cFC) {
        return new ModelMapper().map(cFC, CFCResponseDto.class);
    }

    public static List<CFCResponseDto> toListDto(List<CFC> cfcs) {
        return cfcs.stream().map(cfc -> toDto(cfc)).collect(Collectors.toList());
    }
}
