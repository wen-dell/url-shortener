package br.com.urlshortener.domain.service.criteria;

import org.springframework.data.jpa.domain.Specification;

import br.com.urlshortener.domain.model.UserURL;
import lombok.Data;

@Data
public class UserURLCriteria {

	private Long userId;

	public static Specification<UserURL> user(Long userId) {
		return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId));
	}
}
