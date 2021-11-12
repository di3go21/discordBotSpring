package com.discbotback.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.discbotback.model.Usuario;
import com.discbotback.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public Usuario crear(Usuario u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return usuRepo.save(u);
	}
	
	public Usuario buscarPorEmail(String email) {
		return usuRepo.findFirstByEmail(email);
	}
	
	public Usuario buscarPorNickdiscord(String nickdiscord) {
		return usuRepo.findFirstByNickdiscord(nickdiscord);
	}
	
	public Usuario getPorId(long id) {
		return usuRepo.findById(id).orElse(null);
	}
	
	public Usuario guardar(Usuario u) {
		return usuRepo.save(u);
	}
	
}
