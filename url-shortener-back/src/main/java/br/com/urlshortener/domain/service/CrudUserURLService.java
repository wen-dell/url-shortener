package br.com.urlshortener.domain.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.urlshortener.domain.exception.BusinessException;
import br.com.urlshortener.domain.model.UserURL;
import br.com.urlshortener.domain.repository.UserURLRepository;

@Service
public class CrudUserURLService {
	
	@Autowired
	private UserURLRepository userURLRepository;
	
	public UserURL save(UserURL userURL) {
		
		userURL.setDate(new Date());
		userURL.setCode("AAA");
		
		try {
			URL url = new URL(userURL.getOriginalUrl());
		} catch (MalformedURLException e) {
			throw new BusinessException("URL informada é inválida");			
		}
		
		return userURLRepository.save(userURL);
	}

}
