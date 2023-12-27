package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.web.dto.EnderecoCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.EnderecoResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnderecoMapper {
	
	public static final ModelMapper modelMapper = new ModelMapper();

    public static Endereco toEndereco(EnderecoCreateDto dto) {
    	return (dto != null ) ? modelMapper.map(dto, Endereco.class) : null;
    		
    }       
    

    public static EnderecoResponseDto toDto(Endereco endereco) {
        return new ModelMapper().map(endereco, EnderecoResponseDto.class);
    }

}
