package br.gov.ce.detran.vistoriacfcapi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import br.gov.ce.detran.vistoriacfcapi.entity.StatusAgendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a WHERE a.cFC.id = :cfcId AND a.ativo = true")
    List<Agendamento> findAllByCfc(@Param("cfcId") Long cfcId);

    @Query("SELECT a FROM Agendamento a where a.dataHoraAgendamento BETWEEN :inicio AND :fim AND a.ativo = true")
    List<Agendamento> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COUNT(a) > 0 FROM Agendamento a " +
            "WHERE a.cFC.id = :cfcId " +
            "AND a.dataHoraAgendamento = :dataHora " +
            "AND a.status NOT IN ('CANCELADO') " +
            "AND a.ativo = true")
    boolean existsAgendamentoNaData(Long cfc, LocalDateTime dataHora);

    @Query("SELECT a FROM Agendamento a " +
            "WHERE a.status = :status " +
            "AND a.dataHoraAgendamento >= :dataInicio " +
            "AND a.ativo = true " +
            "ORDER BY a.dataHoraAgendamento")
    List<Agendamento> findByStatusAndDataFutura(StatusAgendamento status, LocalDateTime dataInicio);

    @Query("SELECT COUNT(a) FROM Agendamento a " +
            "WHERE a.cFC.id = :cfcId " +
            "AND a.dataHoraAgendamento BETWEEN :inicio AND :fim " +
            "AND a.status NOT IN ('CANCELADO') " +
            "AND a.ativo = true")
    long countAgendamentosNoPeriodo(Long cfc, LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COUNT(a) FROM Agendamento a WHERE DATE(a.dataHoraAgendamento) = :date AND a.ativo = true")
    long countByDataHoraAgendamentoDate(LocalDate date);

    @Query("SELECT a FROM Agendamento a WHERE DATE(a.dataHoraAgendamento) = :date AND a.dataHoraAgendamento >= :horaInicio AND a.dataHoraAgendamento < :horaFim AND a.ativo = true")
    List<Agendamento> findByDataHoraAgendamento(LocalDate date, LocalTime horaInicio, LocalTime horaFim);
}

