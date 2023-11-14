package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.Cliente;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ClienteCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {
    

    public static Cliente toCliente(ClienteCreateDto dto) {
        return new ModelMapper().map(dto, Cliente.class);
    }

    public static ClienteResponseDto toDto(Cliente cliente) {
        return new ModelMapper().map(cliente, ClienteResponseDto.class);
    }
}
