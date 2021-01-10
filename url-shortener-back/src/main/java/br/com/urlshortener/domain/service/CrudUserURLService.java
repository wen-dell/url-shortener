package br.com.urlshortener.domain.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.urlshortener.domain.exception.BusinessException;
import br.com.urlshortener.domain.model.UserURL;
import br.com.urlshortener.domain.repository.UserURLRepository;
import br.com.urlshortener.domain.service.criteria.UserURLCriteria;
import br.com.urlshortener.util.CodeGeneratorUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CrudUserURLService {
	
	@Autowired
	private UserURLRepository userURLRepository;
	
//	public Page<UserURL> findByCriteria(UserURLCriteria criteria, Pageable page) {
//		log.debug("find by criteria : {}, page: {}", criteria, page);
//		final Specification<UserURL> specification = createSpecifications(criteria);
//		return userURLRepository.findAll(specification, page);
//	}
//
//	private Specification<UserURL> createSpecifications(UserURLCriteria criteria) {
//		Specification<UserURL> specification = Specification.where(null);
//
//		if (criteria == null) return specification;
//		if (criteria.getUserId() != null) {
//			specification = specification.and(UserURLCriteria.user(criteria.getUserId()));
//		}
//
//		return specification;
//	}

	
	public UserURL save(UserURL userURL) {
		
		userURL.setDate(new Date());
		userURL.setCode(CodeGeneratorUtil.generate());
		userURL.setGeneratedUrl("http://localhost:4200/" + userURL.getCode());
		
		try {
			new URL(userURL.getOriginalUrl());
		} catch (MalformedURLException e) {
			throw new BusinessException("URL informada é inválida");			
		}
		
		return userURLRepository.save(userURL);
	}

}
