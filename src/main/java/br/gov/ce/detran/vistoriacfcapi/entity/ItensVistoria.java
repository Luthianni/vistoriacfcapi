package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "itens_vistorias")
@EntityListeners(AuditingEntityListener.class)
public class ItensVistoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    public enum VistoriaStatus {
    	SIM, NAO
    }

    @Column(name = "salas", nullable = false)
    @Enumerated(EnumType.STRING)
    private VistoriaStatus sala;

    @Column(name = "banheiros", nullable = false)
    @Enumerated(EnumType.STRING)
    private VistoriaStatus banheiro;

    @Column(name = "equipamentos", nullable = false)
    @Enumerated(EnumType.STRING)
    private VistoriaStatus treinamento;

    @Column(name = "portador_deficiencia", nullable = false)
    @Enumerated(EnumType.STRING)
    private VistoriaStatus portadorDeficiencia;

    @Column(name = "suporte_alunos", nullable = false)
    @Enumerated(EnumType.STRING)
    private VistoriaStatus suporte;

    @Size(max = 255)
    @Column(name = "observacao")
    private String observacao;

    @CreatedDate
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	@LastModifiedDate
	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;
	
	@CreatedBy
	@Column(name = "criado_por")
	private String criadoPor;

	@LastModifiedBy
	@Column(name = "modificado_por")
	private String modificadopor;

    
}
