package com.discbotback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.discbotback.model.Entradilla;
import com.discbotback.model.Usuario;
import com.discbotback.servicios.EntradillaService;
import com.discbotback.servicios.UsuarioService;
import com.discbotback.upload.StorageService;

@Controller
public class AudioController {

	@Autowired
	private StorageService storageService;
	@Autowired
	EntradillaService entradillaService;

	@Autowired
	UsuarioService usuServ;

	@GetMapping("/delete/{id}")
	public String deleteAudio(@PathVariable long id) {

		//TODO: si elimina solo quiero que se elimine de seleccionada de los usuarios
		
		//añadir nuevo atributo a audio con autor

		// check if is selected
		Usuario usuarioAutenticado = getUsuarioAutenticado();
		
		Entradilla seleccionada = usuarioAutenticado.getSeleccionada();

		if (seleccionada != null)
			if (id== seleccionada.getId()) {
				usuarioAutenticado.setSeleccionada(null);
				usuServ.guardar(usuarioAutenticado);
			}
		List<Entradilla> entradillas = usuarioAutenticado.getEntradillas();
		Entradilla aBorrar = entradillaService.findById(id);
		entradillas.remove(aBorrar);
		usuServ.guardar(usuarioAutenticado);
		

		return "redirect:/personal";

	}
	
	
	

	@GetMapping("/add/lista/{id}")
	public String addAudioToMyList(@PathVariable long id) {

		Entradilla toAdd = entradillaService.findById(id);
		Usuario usu = getUsuarioAutenticado();
		usu.getEntradillas().add(toAdd);
		usuServ.guardar(usu);
		return "redirect:/personal";
	}

	@GetMapping("/select/{id}")
	public String selectAudio(@PathVariable long id) {

		Usuario usuarioAutenticado = getUsuarioAutenticado();
		Entradilla finded = entradillaService.findById(id);
		usuarioAutenticado.setSeleccionada(finded);
		usuServ.guardar(usuarioAutenticado);
		return "redirect:/personal";
	}

	@PostMapping("/uploadaudio")
	public String uploadAudio(@RequestParam("file") MultipartFile file, @RequestParam String desc) {

		if (desc == null)
			desc = "";
		Usuario usuAutenticado = getUsuarioAutenticado();

		if (!file.isEmpty()) {
			String audio = storageService.store(file);

			String uriString = MvcUriComponentsBuilder.fromMethodName(PrincipalController.class, "serveFile", audio)
					.build().toUriString();
			Entradilla newEntrad = new Entradilla(desc.isEmpty() ? "Sin desc." : desc, uriString, usuAutenticado);
			entradillaService.save(newEntrad);
			
			usuAutenticado.getEntradillas().add(newEntrad);
			usuServ.guardar(usuAutenticado);
		}

		return "redirect:/personal";
	}

	private Usuario getUsuarioAutenticado() {
		String nickDeUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario usuAutenticado = usuServ.buscarPorNickdiscord(nickDeUsuario);
		return usuAutenticado;
	}

}
