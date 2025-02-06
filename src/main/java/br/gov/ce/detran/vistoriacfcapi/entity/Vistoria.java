package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vistorias")
@EntityListeners(AuditingEntityListener.class)
public class Vistoria extends Entidade implements Serializable {

    private static final long serialVersionUID = 1L;
    
      
    @Column(name = "data_agendada", nullable = false)
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataAgendada;

    @ManyToOne
    @JoinColumn(name = "id_cfc_fk", nullable = true)
    private CFC cFC;       

    @ManyToOne
    @JoinColumn(name = "id_admin_fk", nullable = false)
    private Usuario administrador;

	@ManyToOne
	@JoinColumn(name = "id_vistoriador1_fk", nullable = false)
	private Usuario vistoriador1;

	@ManyToOne
	@JoinColumn(name = "id_vistoriador2_fk", nullable = false)
	private Usuario vistoriador2;

	@Column(name = "salas", nullable = false)
    private boolean sala;

    @Column(name = "banheiros", nullable = false)
    private boolean banheiro;

    @Column(name = "equipamentos", nullable = false)
    private boolean treinamento;

    @Column(name = "portador_deficiencia", nullable = false)
    private boolean portadorDeficiencia;

    @Column(name = "suporte_alunos", nullable = false)
    private boolean suporte;

    @Size(max = 255)
    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;


    public void setUsuario(Usuario buscarPorId) {
        
        throw new UnsupportedOperationException("Unimplemented method 'setUsuario'");
    }

   	
}
