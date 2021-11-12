package com.discbotback.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.discbotback.model.Usuario;
import com.discbotback.servicios.UsuarioService;

@Controller
public class LoginController {
	@Autowired
	UsuarioService usuServ;
	
	
	@GetMapping("/auth/login")
	public String login(Model model) {
		boolean autenticado = isAutenticado();
		
		if(autenticado)
			return "redirect:/personal";
		
		return "login";
	}


	

	@GetMapping("/registro")
	public String registro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registroForm";
	}

	

	@PostMapping("/registro")
	public String registroPost(@Valid @ModelAttribute Usuario newUsu) {
		usuServ.crear(newUsu);
		return "login";
	}


	private boolean isAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		boolean autenticado = !name.equals("anonymousUser");
		return autenticado;
	}
}
