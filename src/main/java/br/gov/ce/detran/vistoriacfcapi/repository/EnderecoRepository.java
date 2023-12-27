package br.gov.ce.detran.vistoriacfcapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;

public interface EnderecoRepository extends JpaRepository <Endereco, Long>{
    
}
