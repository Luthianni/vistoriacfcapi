package br.gov.ce.detran.vistoriacfcapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ce.detran.vistoriacfcapi.entity.Usuario;
import br.gov.ce.detran.vistoriacfcapi.entity.Usuario.Role;
import br.gov.ce.detran.vistoriacfcapi.exception.EntityNotFoundException;
import br.gov.ce.detran.vistoriacfcapi.exception.UsernameUniqueViolationException;
import br.gov.ce.detran.vistoriacfcapi.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			return usuarioRepository.save(usuario);
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(
					String.format("Username {%s} já cadastrado", usuario.getUsername()));
		}

	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado.", id)));
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		if (!novaSenha.equals(confirmaSenha)) {
			throw new RuntimeException("Nova senha não confere com confirmação de senha.");
		}
		Usuario user = buscarPorId(id);
		if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
			throw new RuntimeException("Sua senha não confere.");
		}

		user.setPassword(passwordEncoder.encode(novaSenha));
		return user;
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
    Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
    if (usuarioOptional.isPresent()) {
        Usuario usuario = usuarioOptional.get();
        log.info("Usuário encontrado - ID: {}, Username: {}", usuario.getId(), usuario.getUsername());
        return usuario;
    } else {
        log.warn("Usuário não encontrado para o username: {}", username);
        throw new EntityNotFoundException(String.format("Usuário com %s não encontrado.", username));
    }
}

	@Transactional(readOnly = true)
	public Role buscarRolePorUsername(String username) {
		Optional<Usuario.Role> roleOptional = usuarioRepository.findRoleByUsername(username);
		
		return roleOptional.orElseThrow(() -> new RuntimeException("Role não encontrada para o username: " + username));
	}
	
}