package br.gov.ce.detran.vistoriacfcapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.Servidor;
import br.gov.ce.detran.vistoriacfcapi.exception.CpfUniqueViolationException;
import br.gov.ce.detran.vistoriacfcapi.repository.ServidorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ServidorService {
    
    private final ServidorRepository servidorRepository;

    @Transactional
    public Servidor salvar(Servidor servidor) {
        try{
            return servidorRepository.save(servidor); 
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", servidor.getCpf())
            );
        }
    }

    @Transactional
    public Servidor buscarPorId(Long id) {
        return servidorRepository.findById(id).orElseThrow(
            ()-> new EntityNotFoundException(String.format("Servidor id=%s não encontrado.", id)));

    }
}
