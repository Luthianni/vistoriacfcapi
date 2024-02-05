package br.gov.ce.detran.vistoriacfcapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.CentroFormacaoCondutor;
import br.gov.ce.detran.vistoriacfcapi.exception.CnpjUniqueViolationException;
import br.gov.ce.detran.vistoriacfcapi.repository.CentroFormacaoCondutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CentroFormacaoCondutorService {
    
    private final CentroFormacaoCondutorRepository centroFormacaoCondutorRepository;

    @Transactional
    public CentroFormacaoCondutor salvar(CentroFormacaoCondutor centroFormacaoCondutor) {
        String cnpj = centroFormacaoCondutor.getCnpj();

        if(centroFormacaoCondutorRepository.existsByCnpj(cnpj)) {
            throw new CnpjUniqueViolationException(
                String.format("CNPJ '%s não pode ser cadastrado, já existe no sistema", cnpj)
            );
        }

        return centroFormacaoCondutorRepository.save(centroFormacaoCondutor);
    }

    @Transactional(readOnly = true)
    public CentroFormacaoCondutor buscarPorId(Long id) {
        return centroFormacaoCondutorRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("CFC id=%s não encontrado no sistema", id))); 
    }

}
