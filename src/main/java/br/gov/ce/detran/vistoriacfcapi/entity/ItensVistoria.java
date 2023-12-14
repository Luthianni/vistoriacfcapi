package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter @Setter
// @NoArgsConstructor
@Data
@Entity
@Table(name = "itens_vistorias")
@EntityListeners(AuditingEntityListener.class)
public class ItensVistoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "salas", nullable = false)
    private boolean sala;

    @Column(name = "banheiros", nullable = false)
    private boolean banheiro;

    @Column(name = "equipamentos", nullable = false)
    private boolean treinamento;

    @Column(name = "pcd", nullable = false)
    private boolean pcd;

    @Column(name = "suporte_alunos", nullable = false)
    private boolean suporte;

    @Column(name = "observacao")
    private String observacao;

    @CreatedDate
	@Column(name = "data_criacao")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime dataCriacao;

	@LastModifiedDate
	@Column(name = "data_modificacao")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDateTime dataModificacao;
	
	@CreatedBy
	@Column(name = "criado_por")
	private String criadoPor;

	@LastModifiedBy
	@Column(name = "modificado_por")
	private String modificadopor;

    
}
