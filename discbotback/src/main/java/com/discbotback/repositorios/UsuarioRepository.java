package com.discbotback.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discbotback.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findFirstByEmail(String email);
	Usuario findFirstByNickdiscord(String nickdiscord);

}
