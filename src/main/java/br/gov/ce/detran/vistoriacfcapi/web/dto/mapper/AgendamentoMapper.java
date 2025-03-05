package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoResponseDto;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AgendamentoMapper {
          
    public static Agendamento toAgendamento(AgendamentoCreateDto dto) {
        return new ModelMapper().map(dto, Agendamento.class);
    }

    public static AgendamentoResponseDto toDto(Agendamento agendamento) {
        return new ModelMapper().map(agendamento, AgendamentoResponseDto.class);
    }

    public static List<AgendamentoResponseDto> toListDto(List<Agendamento> agendamentos) {
        return agendamentos.stream().map(agendamento -> toDto(agendamento)).collect(Collectors.toList());
    }

    
}