package br.gov.ce.detran.vistoriacfcapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;

public interface VistoriaRepository extends JpaRepository <Vistoria, Long> {
    
}
