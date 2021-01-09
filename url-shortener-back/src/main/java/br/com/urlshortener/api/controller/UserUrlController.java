package br.com.ouvidoriamb.api.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.urlshortener.data.ApiResponse;
import br.com.urlshortener.data.PaginatedData;
import br.com.urlshortener.data.Pagination;
import br.com.urlshortener.domain.model.User;
import br.com.urlshortener.domain.repository.UserRepository;
import br.com.urlshortener.domain.service.CrudUserURLService;
import br.com.urlshortener.domain.service.criteria.UserCriteria;

@RestController
@RequestMapping("/users")
public class UserUrlController {

	private final List<Integer> SIZE_DEFAULT = Arrays.asList(5, 10, 15, 20);

	@Autowired
	private CrudUserURLService crudUserURLService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<ApiResponse> listAllUserUrl(
			@SortDefault.SortDefaults({ @SortDefault(sort = "id", direction = Sort.Direction.DESC) }) Pageable pageable,
			UserCriteria userCriteria) {
		if (!SIZE_DEFAULT.contains(pageable.getPageSize())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.of("Informe um tamanho válido: 5, 10, 15 ou 20"));
		}

		final Page<User> pagedRevenue = crudUserURLService.findByCriteria(userCriteria, pageable);

		final List<User> users = pagedRevenue.getContent();

		final Pagination pagination = Pagination.from(pagedRevenue, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of("", new PaginatedData<>(users, pagination)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User insert(@Valid @RequestBody User user) {
		return crudUserURLService.save(user);
	}

}
