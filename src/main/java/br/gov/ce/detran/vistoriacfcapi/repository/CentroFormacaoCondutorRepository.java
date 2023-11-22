package br.gov.ce.detran.vistoriacfcapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.CentroFormacaoCondutor;

public interface CentroFormacaoCondutorRepository extends JpaRepository <CentroFormacaoCondutor, Long>{
    
}
