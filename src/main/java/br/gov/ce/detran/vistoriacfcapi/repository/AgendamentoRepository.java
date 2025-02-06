package br.gov.ce.detran.vistoriacfcapi.repository;

import br.gov.ce.detran.vistoriacfcapi.entity.Agendamento;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgengamentoRepository extends JpaRepository<Agendamento, Long> {

    @Query("SELECT a FROM Agendamento a WHERE a.cfc.id = :cfcId AND a.ativo = true")
    List<Agendamento> findAllByCfc(Long cfcId);

    @Query("SELECT a FROM Agendamento a where a.dataHoraAgendamento BETWEEN :inicio AND :fim AND a.ativo = true")
    List<Agendamento> findByPeriodo(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COUNT(a) > 0 FROM Agendamento a " +
            "WHERE a.cfc.id = :cfcId " +
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
            "WHERE a.cfc.id = :cfcId " +
            "AND a.dataHoraAgendamento BETWEEN :inicio AND :fim " +
            "AND a.status NOT IN ('CANCELADO') " +
            "AND a.ativo = true")
    long countAgendamentosNoPeriodo(UUID cfcId, LocalDateTime inicio, LocalDateTime fim);
}
