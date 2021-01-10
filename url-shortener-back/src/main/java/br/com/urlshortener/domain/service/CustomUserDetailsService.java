package br.com.urlshortener.domain.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.urlshortener.domain.model.User;
import br.com.urlshortener.domain.repository.UserRepository;

@Service(value = "customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(),
				user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")));
		return userDetails;
	}

}
