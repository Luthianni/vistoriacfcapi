package br.gov.ce.detran.vistoriacfcapi.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.exception.CnpjUniqueViolationException;
import br.gov.ce.detran.vistoriacfcapi.repository.CFCRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CFCService {
    
    private final CFCRepository cFCRepository;

    @Transactional
    public CFC salvar(CFC cFC) {
        String cnpj = cFC.getCnpj();

        if(cFCRepository.existsByCnpj(cnpj)) {
            throw new CnpjUniqueViolationException(
                String.format("CNPJ '%s não pode ser cadastrado, já existe no sistema", cnpj)
            );
        }

        return cFCRepository.save(cFC);
    }

    @Transactional(readOnly = true)
    public CFC buscarPorId(Long id) {
        return cFCRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("CFC id=%s não encontrado no sistema", id))); 
    }

    @Transactional(readOnly = true)
    public List<CFC> buscarTodos() {
        return cFCRepository.findAll();
    }  
   
}
