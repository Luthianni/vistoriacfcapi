package br.gov.ce.detran.vistoriacfcapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "agendamentos", indexes = {
        @Index(name = "idx_cfc_data", columnList = "id_cfc,data_hora_agendamento"),
        @Index(name = "idx_status", columnList = "status")
})
@SQLDelete(sql = "UPDATE agendamentos SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Agendamento extends Entidade implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cfc_fk", nullable = true)
    private CFC cFC;

    @ManyToOne
    @JoinColumn(name = "id_endereco_fk", nullable = false)
    private Endereco endereco;

    @Column(name = "data_hora_agendamento")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dataHoraAgendamento;

    @Column(name = "data_hora_preferencia")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime dataHoraPreferencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status = StatusAgendamento.PENDENTE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVistoria tipoVistoria;

    @Column(length = 1000)
    private String observacoes;

    @Column(nullable = false)
    private boolean primeiraVistoria;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "agendamento")
    private Set<Vistoria> vistorias = new HashSet<>();

    public void cancelarAgendamento(String motivo) {
        if (this.status == StatusAgendamento.REALIZADO) {
            throw new IllegalStateException("Não é possível cancelar um agendamento já realizado");
        }
        this.status = StatusAgendamento.CANCELADO;
        this.observacoes = motivo;
    }
}
