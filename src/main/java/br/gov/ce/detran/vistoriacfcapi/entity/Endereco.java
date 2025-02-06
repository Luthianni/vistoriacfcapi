package br.gov.ce.detran.vistoriacfcapi.entity;

import java.io.Serializable;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "endereco")
@EntityListeners(AuditingEntityListener.class)
public class Endereco extends Entidade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "numero", nullable = false)
    private String numero;

    private String complemento;
    
    @OneToOne
    @JoinColumn(name = "id_cfc_fk", nullable = true)
    private CFC cFC;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_endereco")
    private TipoEndereco tipoEndereco;

    
    @Override
    public String toString() {
        StringBuilder enderecoCompleto = new StringBuilder();
        enderecoCompleto.append(endereco)
                        .append(", nº ")
                        .append(numero)
                        .append(", ")
                        .append(complemento)
                        .append(" _ ")
                        .append(bairro)
                        .append(". ")
                        .append(cidade)
                        .append(" - ")
                        .append(estado)
                        .append(". CEP: ")
                        .append(cep);
        return enderecoCompleto.toString();
    }
	


    public void setUsuario(Usuario buscarPorId) {
		
		
	}
    

}
