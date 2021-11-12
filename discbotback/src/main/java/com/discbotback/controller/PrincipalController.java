package com.discbotback.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.discbotback.model.Entradilla;
import com.discbotback.model.Usuario;
import com.discbotback.servicios.EntradillaService;
import com.discbotback.servicios.UsuarioService;
import com.discbotback.upload.StorageService;



@Controller
public class PrincipalController {

	@Autowired
	private StorageService storageService ;
	@Autowired
	UsuarioService usuServ;
	@Autowired
	EntradillaService entradillaService;

	@GetMapping({"/home","/"})
	public String getHome(){
		
		if(isAutenticado())
				return "redirect:/personal";
		return "login";
	}
	
	@GetMapping("/personal")
	public String paginaPersonal(Model model){
		List<Entradilla> misEntradillas = getUsuarioAutenticado().getEntradillas();
		Entradilla seleccionada = getUsuarioAutenticado().getSeleccionada();
		model.addAttribute("entradillas", misEntradillas);
		
		List<Entradilla> todas = entradillaService.findAll();
		todas= todas.stream()
				.filter(a->!misEntradillas.contains(a))
				.collect(Collectors.toList());
		model.addAttribute("allAudios", todas);
		model.addAttribute("selectedId", seleccionada==null?0:seleccionada.getId());
		model.addAttribute("nick", getUsuarioAutenticado().getNickdiscord());
		
		return "privado/personalArea";
	}
	

	
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename){
		
		Resource file= storageService.loadAsResource(filename);
		
		return ResponseEntity.ok().body(file);
	}
	
	private Usuario getUsuarioAutenticado() {
		String nickDeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuAutenticado = usuServ.buscarPorNickdiscord(nickDeUsuario);
		return usuAutenticado;
	}
	
	private boolean isAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		boolean autenticado = !name.equals("anonymousUser");
		return autenticado;
	}
}
