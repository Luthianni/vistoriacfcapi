package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Getter @Setter
@Entity
@Table(name = "Profile")
@SQLDelete(sql = "UPDATE agendamentos SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@EntityListeners(AuditingEntityListener.class)
public class Profile extends Pessoa implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @OneToOne
    @JoinColumn(name = "id_usuario_fk", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private boolean ativo = true;

}
