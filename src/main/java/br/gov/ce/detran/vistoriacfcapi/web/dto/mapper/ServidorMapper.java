package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import br.gov.ce.detran.vistoriacfcapi.entity.Servidor;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ServidorCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ServidorResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServidorMapper {

    public static Servidor toServidor(ServidorCreateDto dto) {
        return new ModelMapper().map(dto, Servidor.class);
    }

    public static ServidorResponseDto toDto(Servidor servidor) {
        return new ModelMapper().map(servidor, ServidorResponseDto.class);
    }
    
}
