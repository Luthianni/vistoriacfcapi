package br.gov.ce.detran.vistoriacfcapi.service;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.Endereco;
import br.gov.ce.detran.vistoriacfcapi.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class EnderecoService {
    
  
    private final EnderecoRepository enderecoRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(EnderecoService.class);

    public Optional<Endereco> buscarPorCepENumero(String cep, String numero, String endereco) {
        return enderecoRepository.findByCepAndNumeroAndEndereco(cep, numero, endereco);
    }


    @Transactional
    public Endereco salvar(Endereco endereco) {   
    	LOGGER.info("Salvando endereço: {}", endereco);
        return enderecoRepository.save(endereco);

    }

    @Transactional(readOnly = true)
    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Endereço id=%s não encontrado no sistema"+id)); 
    }

   

}
