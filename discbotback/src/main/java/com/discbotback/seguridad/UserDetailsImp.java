package com.discbotback.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.discbotback.model.Usuario;
import com.discbotback.repositorios.UsuarioRepository;

@Service
public class UserDetailsImp implements UserDetailsService{

	
	@Autowired
	UsuarioRepository usuRepo;
	
	@Override
	public UserDetails loadUserByUsername(String nickdiscord)  {
		Usuario usu = usuRepo.findFirstByNickdiscord(nickdiscord);
		UserBuilder builder=null;
		
		if(usu==null) {
			throw new UsernameNotFoundException(nickdiscord);
		}
		builder = User.withUsername(usu.getNickdiscord());
		builder.disabled(false);
		builder.password(usu.getPassword());
		builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		
		return builder.build();
	}

}
