package br.gov.ce.detran.vistoriacfcapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.Cliente;
import br.gov.ce.detran.vistoriacfcapi.exception.CpfUniqueViolationException;
import br.gov.ce.detran.vistoriacfcapi.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {
    
    private final ClienteRepository ClienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return ClienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf())
                );
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return ClienteRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))); 
    }

}
