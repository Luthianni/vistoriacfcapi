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

@Getter
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
        validarCamposObrigatorios(endereco);
        Optional<Endereco> enderecoExistente = buscarPorCepENumero(
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getEndereco()
        );
        if (enderecoExistente.isPresent()) {
            LOGGER.warn("Endereço já existe: {}", enderecoExistente.get().getId());
            throw new IllegalStateException("Endereço já cadastrado no sistema com ID: " + enderecoExistente.get().getId());
        }
        return enderecoRepository.save(endereco);
    }

    @Transactional(readOnly = true)
    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Endereço id=%d não encontrado no sistema", id)));
    }

    private void validarCamposObrigatorios(Endereco endereco) {
        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }
        if (endereco.getEstado() == null || endereco.getEstado().trim().isEmpty()) {
            throw new IllegalArgumentException("Estado é obrigatório");
        }
        if (endereco.getEndereco() == null || endereco.getEndereco().trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço é obrigatório");
        }
        if (endereco.getCep() == null || endereco.getCep().trim().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório");
        }
        if (endereco.getNumero() == null || endereco.getNumero().trim().isEmpty()) {
            throw new IllegalArgumentException("Número é obrigatório");
        }
    }
}