package br.gov.ce.detran.vistoriacfcapi.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.exception.CnpjUniqueViolationException;
import br.gov.ce.detran.vistoriacfcapi.repository.CFCRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class CFCService {
    
    private final CFCRepository cFCRepository;

    
    @Transactional
    public CFC salvar(CFC cFC) {
        String cnpj = cFC.getCnpj();

        // if(cFCRepository.findByCnpj(cnpj)) {
        //     throw new CnpjUniqueViolationException(
        //         String.format("CNPJ '%s não pode ser cadastrado, já existe no sistema", cnpj)
        //     );
        // }

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

    public Optional<CFC> buscarPorCnpj(String cnpj) {
        return cFCRepository.findByCnpj(cnpj);
    }

   
}
