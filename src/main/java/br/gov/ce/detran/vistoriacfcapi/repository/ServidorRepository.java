package br.gov.ce.detran.vistoriacfcapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.Servidor;

public interface ServidorRepository extends JpaRepository <Servidor, Long> {
    
}
