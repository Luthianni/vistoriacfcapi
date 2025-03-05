package br.gov.ce.detran.vistoriacfcapi.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.repository.AgendamentoRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.CFCRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.UsuarioRepository;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoFiltroDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.HorarioDisponivelDto;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@RequiredArgsConstructor
@Service
@Slf4j
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CFCRepository cfcRepository;

    @Value("${agendamento.limite-diario:3}")
    private int limiteDiario;

    public Page<Agendamento> buscarAgendamentos(AgendamentoFiltroDto filtro, Pageable pageable) {
        return agendamentoRepository.findAll(pageable);
    }

    @Transactional
    public List<Agendamento> buscarAgendamentos() {
        return agendamentoRepository.findAll();
    }

    public AgendamentoResponseDto createAgendamento(Agendamento agendamento) {
        validarDadosAgendamento(agendamento);
        validarHorarioAgendamento(agendamento.getDataHoraAgendamento());
        // validarDisponibilidade(agendamento.getId(),
        // agendamento.getDataHoraAgendamento());
        // Other logic...
        return new AgendamentoResponseDto(agendamento);
    }

    private void validarHorarioAgendamento(LocalDateTime dataHoraAgendamento) {
        DayOfWeek diaSemana = dataHoraAgendamento.getDayOfWeek();
        LocalTime hora = dataHoraAgendamento.toLocalTime();

        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Agendamentos são permitidos apenas de segunda a sexta-feira.");
        }

        if (hora.isBefore(LocalTime.of(8, 0)) || hora.isAfter(LocalTime.of(17, 0))) {
            throw new IllegalArgumentException("O horário permitido para agendamentos é entre 08:00 e 17:00.");
        }
    }

    // private void validarDisponibilidade(Long id, LocalDateTime
    // dataHoraAgendamento) {
    // // Example implementation to check availability
    // boolean isAvailable = agendamentoRepository.isAvailable(id,
    // dataHoraAgendamento);
    // if (!isAvailable) {
    // throw new IllegalArgumentException("O horário não está disponível para
    // agendamento.");
    // }
    // }

    private void validarDadosAgendamento(Agendamento agendamento) {
        if (agendamento.getCFC() == null) {
            throw new IllegalArgumentException("CFC ID é obrigatório");
        }
        if (agendamento.getUsuario() == null) {
            throw new IllegalArgumentException("Usuário ID é obrigatório");
        }
        if (agendamento.getDataHoraAgendamento() == null) {
            throw new IllegalArgumentException("DataHoraAgendamento é obrigatório");
        }
    }

    public void cancelarAgendamento(int i, String motivo) {

        throw new UnsupportedOperationException("Unimplemented method 'cancelarAgendamento'");
    }

    public List<HorarioDisponivelDto> buscarHorariosDisponiveis(int i, Object object) {

        throw new UnsupportedOperationException("Unimplemented method 'buscarHorariosDisponiveis'");
    }
}
