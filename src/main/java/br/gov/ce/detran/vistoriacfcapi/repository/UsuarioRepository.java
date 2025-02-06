package br.gov.ce.detran.vistoriacfcapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import br.gov.ce.detran.vistoriacfcapi.entity.Usuario;
import jakarta.persistence.QueryHint;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByUsername(String username);
    
    @Query("SELECT u.role FROM Usuario u WHERE u.username = :username")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Usuario.Role> findRoleByUsername(String username);

    
}
