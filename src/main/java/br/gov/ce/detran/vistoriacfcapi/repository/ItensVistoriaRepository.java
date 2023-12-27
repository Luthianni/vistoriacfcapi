package br.gov.ce.detran.vistoriacfcapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria;

public interface ItensVistoriaRepository extends JpaRepository <ItensVistoria, Long> {
    
}
