package com.discbotback.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.discbotback.model.Entradilla;
import com.discbotback.model.Usuario;

@Repository
public interface EntradillaRepository extends JpaRepository<Entradilla, Long> {

	List<Entradilla> findByAutor(Usuario usuario);
	
	List<Entradilla> findByDescripcionContainsIgnoreCase(String desc);

}
