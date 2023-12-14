package br.gov.ce.detran.vistoriacfcapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.ItensVistoria;
import br.gov.ce.detran.vistoriacfcapi.repository.ItensVistoriaRepository;
import br.gov.ce.detran.vistoriacfcapi.repository.VistoriaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItensVistoriaService {

    @Autowired
    private ItensVistoriaRepository itensVistoriaRepository;

    @Autowired
    private VistoriaRepository vistoriaRepository;


    @Transactional
    public ItensVistoria salvar(ItensVistoria itensVistoria) {
        
    }
    
}
