package br.com.ouvidoriamb.api.controller;

import br.com.urlshortener.data.ApiResponse;
import br.com.urlshortener.data.PaginatedData;
import br.com.urlshortener.data.Pagination;
import br.com.urlshortener.domain.model.User;
import br.com.urlshortener.domain.repository.UserRepository;
import br.com.urlshortener.domain.service.CrudUserUrlService;
import br.com.urlshortener.domain.service.criteria.UserCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserUrlController {
	
	private final List<Integer> SIZE_DEFAULT = Arrays.asList(5,10,15,20);

	@Autowired
	private CrudUserUrlService crudUserService;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<ApiResponse> listAllUserUrl(@SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.DESC)
	}) Pageable pageable, UserCriteria userCriteria) {
		if(!SIZE_DEFAULT.contains(pageable.getPageSize())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.of("Informe um tamanho valido"));
		}

		final Page<User> pagedRevenue = crudUserService.findByCriteria(userCriteria, pageable);

		final List<User> users = pagedRevenue.getContent();

		final Pagination pagination = Pagination.from(pagedRevenue, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of("", new PaginatedData<>(users, pagination)));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User insert(@Valid @RequestBody User user) {
		return crudUserService.save(user);
	}

	@GetMapping("/login/{userLogin}")
	public ResponseEntity<Boolean> searchByLogin(@PathVariable String userLogin) {

		User user = userRepository.findByLogin(userLogin);

		if (user != null) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
	}

}





