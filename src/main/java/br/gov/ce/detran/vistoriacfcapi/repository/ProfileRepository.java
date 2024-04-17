package br.gov.ce.detran.vistoriacfcapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.ce.detran.vistoriacfcapi.entity.Profile;


public interface ProfileRepository extends JpaRepository<Profile, Long>{
    
    

    // @Query("Select p.role FROM Profile p WHERE p.nome = :nome")
    // @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    // Optional<Profile> findByNome(String nome);

}
