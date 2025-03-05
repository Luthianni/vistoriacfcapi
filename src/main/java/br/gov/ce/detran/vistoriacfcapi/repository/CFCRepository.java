package br.gov.ce.detran.vistoriacfcapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;

public interface CFCRepository extends JpaRepository <CFC, Long>{

   boolean existsByCnpj(String cnpj);

   CFC findByCnpj(String cnpj);
}
