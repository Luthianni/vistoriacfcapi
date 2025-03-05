package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@SuppressWarnings("deprecation")
@Getter @Setter
@Entity
@Table(name = "usuarios")
@SQLDelete(sql = "UPDATE agendamentos SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@EntityListeners(AuditingEntityListener.class)
public class Usuario extends Entidade implements Serializable {

    private static final long serialVersionUID = 1L;
		
    @Column(name = "username", nullable = true, unique = true, length = 100)
	private String username;
	
	@Column(name = "password", nullable = false, length = 200)
	private String password;

	@Column(name = "ultimo_login")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDateTime ultimoLogin;

	@Column(name = "status")
	private String status;

	@Column(name = "active")
	private Boolean active;

	@Column(nullable = false)
    private boolean ativo = true;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 25)
	private Role role = Role.ROLE_VISTORIADOR;
	
	public enum Role {
	
		ROLE_ADMIN, ROLE_VISTORIADOR
	}

   
}
