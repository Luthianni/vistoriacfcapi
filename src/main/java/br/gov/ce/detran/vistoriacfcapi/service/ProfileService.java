package br.gov.ce.detran.vistoriacfcapi.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.Profile;
import br.gov.ce.detran.vistoriacfcapi.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

   
    @Transactional
    public Profile salvar(Profile profile) {
       return profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public Profile buscarPorId(Long id) {
        return profileRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException(String.format("Profile id=%s não encontrado no sistema", id)));
    }

    @Transactional(readOnly = true)
    public List<Profile> buscarTodos() {return profileRepository.findAll();
    }

    
}
