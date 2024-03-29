package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter 
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;
	
	@Column(name = "password", nullable = false, length = 200)
	private String password;

	@Column(name = "nome", nullable = false, length = 200)
	private String nome;

	@Column(name = "cpf", nullable = false, length = 11)
	private String cpf;

	@Column(name = "matricula", nullable = false, length = 6)
	private String matricula;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Column(name = "telefone", nullable = false, length = 11)
	private String telefone;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 25)
	private Role role = Role.ROLE_VISTORIADOR;
	
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
	
	public enum Role {
	
		ROLE_ADMIN, ROLE_VISTORIADOR
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + "]";
	}

		
}
