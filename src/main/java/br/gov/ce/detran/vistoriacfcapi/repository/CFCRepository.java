package br.gov.ce.detran.vistoriacfcapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;

public interface CFCRepository extends JpaRepository <CFC, Long>{

   // boolean existsByCnpj(String cnpj);
    
    Optional<CFC> findByCnpj(String cnpj);
}
