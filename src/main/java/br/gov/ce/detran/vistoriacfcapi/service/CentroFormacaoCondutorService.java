package br.gov.ce.detran.vistoriacfcapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.CentroFormacaoCondutor;
import br.gov.ce.detran.vistoriacfcapi.exception.CpfUniqueViolationException;
import br.gov.ce.detran.vistoriacfcapi.repository.CentroFormacaoCondutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CentroFormacaoCondutorService {
    
    private final CentroFormacaoCondutorRepository centroFormacaoCondutorRepository;

    @Transactional
    public CentroFormacaoCondutor salvar(CentroFormacaoCondutor centroFormacaoCondutor) {
        try {
            return centroFormacaoCondutorRepository.save(centroFormacaoCondutor);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", centroFormacaoCondutor.getCpf())
                );
        }
    }

    @Transactional(readOnly = true)
    public CentroFormacaoCondutor buscarPorId(Long id) {
        return centroFormacaoCondutorRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))); 
    }

}
