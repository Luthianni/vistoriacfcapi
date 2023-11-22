package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.CentroFormacaoCondutor;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CentroFormacaoCondutorCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CentroFormacaoCondutorResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CentroFormacaoCondutorMapper {
    

    public static CentroFormacaoCondutor toCentroFormacaoCondutor(CentroFormacaoCondutorCreateDto dto) {
        return new ModelMapper().map(dto, CentroFormacaoCondutor.class);
    }

    public static CentroFormacaoCondutorResponseDto toDto(CentroFormacaoCondutor centroFormacaoCondutor) {
        return new ModelMapper().map(centroFormacaoCondutor, CentroFormacaoCondutorResponseDto.class);
    }
}
