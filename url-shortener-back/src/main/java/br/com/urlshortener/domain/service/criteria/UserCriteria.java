package br.com.urlshortener.domain.service.criteria;

import br.com.urlshortener.domain.model.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class UserCriteria {

	private String login;

	public static Specification<User> login(String login) {
		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("login"),
				"%" + login.toUpperCase() + "%"));
	}
}
