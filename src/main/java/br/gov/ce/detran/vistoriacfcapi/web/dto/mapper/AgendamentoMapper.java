package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgendamentoMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    //static {
      //  modelMapper.createTypeMap(Agendamento.class, AgendamentoResponseDto.class)
        //        .addMappings(mapper -> {
          //          mapper.map(src -> src.getCFC().getId(), AgendamentoResponseDto::setCfcId);
            //        mapper.map(src -> src.getCFC().getCentroDeFormacao(), AgendamentoResponseDto::setCentroDeFormacao);
              //      mapper.map(src -> src.getCFC().getCnpj(), AgendamentoResponseDto::setCnpj);
                //    mapper.map(src -> src.getCFC().getNomefantasia(), AgendamentoResponseDto::setNomeFantasia);
                  //  mapper.map(src -> src.getUsuario().getId(), AgendamentoResponseDto::setUsuarioId);
                    //mapper.map(src -> src.getEndereco().getId(), AgendamentoResponseDto::setEnderecoId);
                    //mapper.map(src -> src.getTipoVistoria().name(), AgendamentoResponseDto::setTipoVistoria);
                //});
    //}

    public static AgendamentoResponseDto toDto(Agendamento agendamento) {
        return modelMapper.map(agendamento, AgendamentoResponseDto.class);
        // Alternativa: return new AgendamentoResponseDto(agendamento);
    }

    public static List<AgendamentoResponseDto> toListDto(List<Agendamento> agendamentos) {
        return agendamentos.stream()
                .map(AgendamentoMapper::toDto)
                .collect(Collectors.toList());
    }
}