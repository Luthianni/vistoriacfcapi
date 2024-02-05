package br.gov.ce.detran.vistoriacfcapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.Vistoria;
import br.gov.ce.detran.vistoriacfcapi.repository.VistoriaRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class VistoriaService {

    
    private final VistoriaRepository vistoriaRepository;

    public VistoriaService(VistoriaRepository vistoriaRepository) {
        this.vistoriaRepository = vistoriaRepository;
    }

    @Transactional
    public Long agendar(Vistoria vistoria) {
        try {
            return vistoriaRepository.save(vistoria).getId();
        } catch (Exception ex) {
            throw new RuntimeException("Vistoria não pode ser agendada nesta data '%s' ", ex);            
        }
    }

    @Transactional
    public Vistoria buscarPorId(Long id) {
        return vistoriaRepository.findById(id).orElseThrow(
            ()-> new EntityNotFoundException(String.format("Vistoria id=%s não encontrado.", id)));

    }

    @Transactional
    public List<Vistoria> buscarTodos() {
        return vistoriaRepository.findAll();
    }

    // public static Vistoria buscarPorId(Vistoria vistoria) {        
    //     throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    // }
    

}