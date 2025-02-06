package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "cfc")
@EntityListeners(AuditingEntityListener.class)
public class CFC extends Entidade implements Serializable {

    private static final long serialVersionUID = 1L;	
    
    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;  

    @Column(name = "centroDeFormacao", nullable = false, length = 100)
    private String centroDeFormacao;

    @Column(name = "nome_fantasia", nullable = false, length = 100)
    private String fantasia;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "telefone", nullable = false, length = 100)
    private String telefone;
    
    @Column(name = "email", nullable = false, length = 100)
    private String email;  
    
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk", nullable = false)
    private Usuario usuario;



   
}
