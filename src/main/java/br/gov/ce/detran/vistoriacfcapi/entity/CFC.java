package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serial;
import java.io.Serializable;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "cfc")
@SQLDelete(sql = "UPDATE cfc SET ativo = false WHERE id = ?")
@Where(clause = "ativo = true")
@EntityListeners(AuditingEntityListener.class)
public class CFC extends Entidade implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "id_usuario_fk", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_endereco_fk", nullable = false)
    private Endereco endereco;
    
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

    @Column(nullable = false)
    private boolean ativo = true;

    public void setCnpj(String cnpj) {
        if (cnpj != null) {
            this.cnpj = cnpj.replaceAll("[^0-9]", ""); // Remove tudo que não for número
        } else {
            this.cnpj = null;
        }
    }
   
}

