package com.discbotback.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discbotback.model.Entradilla;
import com.discbotback.model.Usuario;
import com.discbotback.repositorios.EntradillaRepository;
import com.discbotback.repositorios.UsuarioRepository;

@Service
public class EntradillaService {

	@Autowired
	EntradillaRepository entradillaRepo;

	@Autowired
	UsuarioRepository usuRepo;
	
	public List<Entradilla> getEntradillasDeAutor(Usuario u) {
		return entradillaRepo.findByAutor(u);
	}
	
	public List<Entradilla> getEntradillasDeUsuario(long id) {
		return usuRepo.findById(id).orElse(null).getEntradillas();
	}

	public List<Entradilla> buscarEntradilla(String query) {
		return entradillaRepo.findByDescripcionContainsIgnoreCase(query);
	}

	public Entradilla findById(long id) {
		return entradillaRepo.findById(id).orElse(null);
	}

	public Entradilla save(Entradilla ent) {
		return entradillaRepo.save(ent);
	}

	public void delete(long id) {
		entradillaRepo.deleteById(id);
	}

	public List<Entradilla> findAll() {
		return entradillaRepo.findAll();
	}
}
