package br.gov.ce.detran.vistoriacfcapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;

public interface EnderecoRepository extends JpaRepository <Endereco, Long>{
    
    Optional<Endereco> findByCepAndNumeroAndEndereco(String cep, String numero, String endereco);
}
