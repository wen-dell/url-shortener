package br.com.urlshortener.domain.service.criteria;

import br.com.urlshortener.domain.model.User;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class UserCriteria {

	private String name;

	public static Specification<User> name(String name) {
		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
				"%" + name.toUpperCase() + "%"));
	}
}
