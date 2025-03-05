package br.gov.ce.detran.vistoriacfcapi.service;


import java.util.List;
import java.util.Optional;

import br.gov.ce.detran.vistoriacfcapi.repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.CFC;
import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
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
    private final EnderecoRepository enderecoRepository;
    
    @Transactional
    public CFC salvar(CFC cfc) {
        // Salva o endereço se ele ainda não tiver um ID
        if (cfc.getEndereco() != null && cfc.getEndereco().getId() == null) {
            Endereco savedEndereco = enderecoRepository.save(cfc.getEndereco());
            cfc.setEndereco(savedEndereco);
        }
        return cFCRepository.save(cfc);
    }

    public CFC buscarPorCnpj(String cnpj) {
        return cFCRepository.findByCnpj(cnpj);
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

   
    public Optional<Endereco> existsByCnpj(String cnpj) {
        
        throw new UnsupportedOperationException("Unimplemented method 'existsByCnpj'");
    }

    
    

     
}
