package br.gov.ce.detran.vistoriacfcapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.entity.StatusAgendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.TipoVistoria;
import br.gov.ce.detran.vistoriacfcapi.entity.Usuario;
import br.gov.ce.detran.vistoriacfcapi.exception.EntityNotFoundException;
import br.gov.ce.detran.vistoriacfcapi.repository.AgendamentoRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.CFCRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.UsuarioRepository;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoFiltroDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.AgendamentoResponseDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.HorarioDisponivelDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class AgendamentoService {

    @Autowired
    private  AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CFCRepository cfcRepository;

    @Value("${agendamento.limite-diario:3}")
    private int limiteDiario;


    public Page<AgendamentoResponseDto> buscarAgendamentos(AgendamentoFiltroDto filtro, Pageable pageable) {
        return agendamentoRepository.findAll(pageable).map(this::convertToResponse);
    }

    public AgendamentoResponseDto cancelarAgendamento(long id, String motivo) {
        log.info("Cancelando agendamento ID: {}", id);

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
                if (agendamento.getDataHoraAgendamento().minusHours(24).isBefore(LocalDateTime.now())) {
                    throw new RuntimeException("Não é possível cancelar com menos de 24h de antecedência");
                }

                agendamento.cancelarAgendamento(motivo);
                agendamento = agendamentoRepository.save(agendamento);

                enviarNotificacaoCancelamento(agendamento);

                return convertToResponse(agendamento);

    }

    public AgendamentoResponseDto criarAgendamento(Agendamento dto) {
    log.info("Iiniciando criação de agendamento para CFC ID: {} na data: {}",
            dto.getCFC(), dto.getDataHoraAgendamento());

    validarDadosAgendamento(convertToResponse(dto));

    CFC cfc = cfcRepository.findById(dto.getId())
    .orElseThrow(() -> new EntityNotFoundException("CFC não encontrado"));

    Usuario usuario = usuarioRepository.findById(dto.getUsuario().getId())
    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

    validarDisponibilidade(cfc.getId(), dto.getDataHoraAgendamento());

    Agendamento agendamento = new Agendamento();
    agendamento.setCFC(cfc);
    agendamento.setUsuario(usuario);
    agendamento.setDataHoraAgendamento(dto.getDataHoraAgendamento());
    agendamento.setDataHoraPreferencia(dto.getDataHoraPreferencia());
    agendamento.setTipoVistoria(TipoVistoria.valueOf(dto.getTipoVistoria().toUpperCase()));
    try {
        agendamento.setTipoVistoria(TipoVistoria.valueOf(dto.getTipoVistoria().toUpperCase()));
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Tipo de vistoria inválido: " + dto.getTipoVistoria());
    }
    agendamento.setPrimeiraVistoria(dto.isPrimeiraVistoria());
    agendamento.setObservacoes(dto.getObservacoes());
    agendamento.setStatus(StatusAgendamento.PENDENTE);

    agendamento = agendamentoRepository.save(agendamento);

    return convertToResponse(agendamento);
    }
    private void validarDisponibilidade(Long id, LocalDateTime dataHoraAgendamento) {
    }

    private void validarDadosAgendamento(AgendamentoResponseDto dto) {
    if (dto.getCfcId() == null) {
        throw new IllegalArgumentException("CFC ID é obrigatório");
            }
            if (dto.getUsuarioId() == null) {
                throw new IllegalArgumentException("Usuário ID é obrigatório");
            } if (dto.getDataHoraAgendamento() == null) {   
        throw new IllegalArgumentException("DataHoraAgendamento é obrigatório");
            }
        }
    @Transactional
    public List<HorarioDisponivelDto> buscarHorariosDisponiveis(long cfc,LocalDate data) {
            log.info("Buscando horários disponíveis para CFC ID: {} na data: {}", cfc,data);
                
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fimDia = data.atTime(LocalTime.MAX);
        
        List<Agendamento> agendamentos = agendamentoRepository.findByPeriodo(inicio, fimDia);         
        
        return List.of();
        }

    @Transactional
    public AgendamentoResponseDto cancelarAgendamento(long cfc,long id, String motivo) {
    log.info("Cancelando agendamento ID: {}", id);

    Agendamento agendamento = buscarAgendamentoAtivo(cfc, id);
    
        if (agendamento.getDataHoraAgendamento().minusHours(24)
        .isBefore(LocalDateTime.now())) {
        throw new RuntimeException("Não é possível cancelar com menos de 24h de antecedência");
        }
    
        agendamento.cancelarAgendamento(motivo);
        agendamento = agendamentoRepository.save(agendamento);
    
        enviarNotificacaoCancelamento(agendamento);
            
                return convertToResponse(agendamento);
                                }
                            
                            
                            
                                private AgendamentoResponseDto convertToResponse(Agendamento agendamento) {
                        // TODO Auto-generated method stub
                        throw new UnsupportedOperationException("Unimplemented method 'convertToResponse'");
                    }
                
                
                
                                private void enviarNotificacaoCancelamento(Agendamento agendamento) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'enviarNotificacaoCancelamento'");
            }
        
        
        
                private Agendamento buscarAgendamentoAtivo(long cfc, long id) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'buscarAgendamentoAtivo'");
        }

}
