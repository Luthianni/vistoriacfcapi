package br.gov.ce.detran.vistoriacfcapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;
import br.gov.ce.detran.vistoriacfcapi.repository.VistoriaRepository;

@Service
public class VistoriaService {

    @Autowired
    private VistoriaRepository vistoriaRepository;

    @Transactional
    public Vistoria agendar(Vistoria vistoria) {
        return vistoriaRepository.save(vistoria);
    }
        
}
