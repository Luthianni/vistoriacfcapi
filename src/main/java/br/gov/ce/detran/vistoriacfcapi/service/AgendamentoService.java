package br.gov.ce.detran.vistoriacfcapi.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.entity.TipoVistoria;
import br.gov.ce.detran.vistoriacfcapi.entity.TipoEndereco;
import br.gov.ce.detran.vistoriacfcapi.entity.Usuario;
import br.gov.ce.detran.vistoriacfcapi.repository.AgendamentoRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.CFCRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.EnderecoRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.UsuarioRepository;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoFiltroDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.HorarioDisponivelDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid; // Importação adicionada
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

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Value("${agendamento.limite-diario:3}")
    private int limiteDiario;

    public Page<Agendamento> buscarAgendamentos(AgendamentoFiltroDto filtro, Pageable pageable) {
        return agendamentoRepository.findAll(pageable);
    }

    @Transactional
    public List<Agendamento> buscarAgendamentos() {
        return agendamentoRepository.findAll();
    }

    @Transactional
    public AgendamentoResponseDto createAgendamento(@Valid AgendamentoCreateDto agendamentoDto) {
        log.info("Criando um novo agendamento: {}", agendamentoDto);

        validarDadosAgendamento(agendamentoDto);
        validarHorarioAgendamento(agendamentoDto.getDataHoraAgendamento());
        validarLimiteDiario(agendamentoDto.getDataHoraAgendamento());
        validarDisponibilidade(null, agendamentoDto.getDataHoraAgendamento());

        Endereco endereco;
        Optional<Endereco> enderecoExistente = enderecoRepository.findByCepAndNumeroAndEndereco(
                agendamentoDto.getCep(),
                agendamentoDto.getNumero(),
                agendamentoDto.getEndereco()
        );
        if (enderecoExistente.isPresent()) {
            endereco = enderecoExistente.get();
            log.info("Endereço existente encontrado: {}", endereco.getId());
        } else {
            endereco = new Endereco();
            endereco.setCep(agendamentoDto.getCep());
            endereco.setNumero(agendamentoDto.getNumero());
            endereco.setEndereco(agendamentoDto.getEndereco());
            endereco.setBairro(agendamentoDto.getBairro());
            endereco.setCidade(agendamentoDto.getCidade());
            endereco.setEstado(agendamentoDto.getEstado());
            //endereco.setComplemento(agendamentoDto.getComplemento());
            //endereco.setTipoEndereco(agendamentoDto.getTipoEndereco() != null
                  //  ? TipoEndereco.valueOf(agendamentoDto.getTipoEndereco().toUpperCase())
                  //  : TipoEndereco.COMERCIAL);
            endereco = enderecoRepository.save(endereco);
            log.info("Novo endereço criado: {}", endereco.getId());
        }

        CFC cfc = cfcRepository.findById(agendamentoDto.getCfcId())
                .orElseThrow(() -> new IllegalArgumentException("CFC não encontrado: " + agendamentoDto.getCfcId()));
        Usuario usuario = usuarioRepository.findById(agendamentoDto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + agendamentoDto.getUsuarioId()));

        Agendamento agendamento = new Agendamento();
        agendamento.setCFC(cfc);
        agendamento.setUsuario(usuario);
        agendamento.setEndereco(endereco);
        agendamento.setDataHoraAgendamento(agendamentoDto.getDataHoraAgendamento());
        agendamento.setDataHoraPreferencia(agendamentoDto.getDataHoraPreferencia());
        agendamento.setTipoVistoria(agendamentoDto.getTipoVistoria() != null
                ? TipoVistoria.valueOf(agendamentoDto.getTipoVistoria().toUpperCase())
                : TipoVistoria.INICIAL);
        agendamento.setObservacoes(agendamentoDto.getObservacoes());
        agendamento.setPrimeiraVistoria(agendamentoDto.getPrimeiraVistoria() != null ? agendamentoDto.getPrimeiraVistoria() : true);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDto(agendamentoSalvo);
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

    private void validarDisponibilidade(Long id, LocalDateTime dataHoraAgendamento) {
        LocalDate dia = dataHoraAgendamento.toLocalDate();
        LocalTime horaInicio = LocalTime.of(dataHoraAgendamento.getHour(), dataHoraAgendamento.getMinute());
        LocalTime horaFim = horaInicio.plusMinutes(1); // Assume que cada agendamento ocupa 1 minuto

        List<Agendamento> agendamentosExistentes = agendamentoRepository.findByDataHoraAgendamento(dia, horaInicio, horaFim);

        agendamentosExistentes.removeIf(a -> a.getId().equals(id));

        if (!agendamentosExistentes.isEmpty()) {
            throw new IllegalStateException("Conflito de agendamento: já existe um agendamento neste horário.");
        }
    }

    private void validarLimiteDiario(LocalDateTime dataHoraAgendamento) {
        LocalDate dia = dataHoraAgendamento.toLocalDate();
        long count = agendamentoRepository.countByDataHoraAgendamentoDate(dia);
        if (count >= limiteDiario) {
            throw new IllegalStateException("Limite diário de " + limiteDiario + " agendamentos atingido para o dia " + dia);
        }
    }

    private void validarDadosAgendamento(AgendamentoCreateDto agendamentoDto) {
        if (agendamentoDto.getCfcId() == null) {
            throw new IllegalArgumentException("CFC ID é obrigatório");
        }
        if (agendamentoDto.getUsuarioId() == null) {
            throw new IllegalArgumentException("Usuário ID é obrigatório");
        }
        if (agendamentoDto.getDataHoraAgendamento() == null) {
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