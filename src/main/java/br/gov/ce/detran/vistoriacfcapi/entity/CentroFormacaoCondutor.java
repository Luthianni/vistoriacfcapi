package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "Cfc")
@EntityListeners(AuditingEntityListener.class)
public class CentroFormacaoCondutor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco_fk", nullable = false)
    private List <Endereco> enderecos;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cnpj", nullable = false, unique = true, length = 11)
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "id_usuario_fk", nullable = false)
    private Usuario usuario;
    
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CentroFormacaoCondutor other = (CentroFormacaoCondutor) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CentroFormacaoCondutor [id=" + id + "]";
    }

    
    
    
}
