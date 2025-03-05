package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.CFCResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CFCMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    static {
        // Desativa mapeamento implícito globalmente para evitar conflitos iniciais
        MODEL_MAPPER.getConfiguration().setImplicitMappingEnabled(false);

        // Configuração explícita do TypeMap
        TypeMap<CFC, CFCResponseDto> typeMap = MODEL_MAPPER.createTypeMap(CFC.class, CFCResponseDto.class);
        typeMap.setProvider(request -> new CFCResponseDto()); // Cria uma instância vazia do DTO
        typeMap.addMappings(mapper -> {
            mapper.map(CFC::getId, CFCResponseDto::setId);
            mapper.map(CFC::getCnpj, CFCResponseDto::setCnpj);
            mapper.map(CFC::getCentroDeFormacao, CFCResponseDto::setCentroDeFormacao);
            mapper.map(CFC::getFantasia, CFCResponseDto::setFantasia);
            mapper.map(CFC::getTipo, CFCResponseDto::setTipo);
            mapper.map(CFC::getTelefone, CFCResponseDto::setTelefone);
            mapper.map(CFC::getEmail, CFCResponseDto::setEmail);
            mapper.map(CFC::getDataCriacao, CFCResponseDto::setData); // Escolha explícita de dataCriacao
            mapper.map(CFC::getUsuario, CFCResponseDto::setUsuario);
            mapper.map(CFC::getEndereco, CFCResponseDto::setEndereco);
        });

        // Reativa mapeamento implícito para outros usos (como toCFC)
        MODEL_MAPPER.getConfiguration().setImplicitMappingEnabled(true);
    }

    public static CFC toCFC(CFCCreateDto dto) {
        return MODEL_MAPPER.map(dto, CFC.class);
    }

    public static CFCResponseDto toDto(CFC cfc) {
        return MODEL_MAPPER.map(cfc, CFCResponseDto.class);
    }

    public static List<CFCResponseDto> toListDto(List<CFC> cfcs) {
        return cfcs.stream().map(CFCMapper::toDto).collect(Collectors.toList());
    }
}