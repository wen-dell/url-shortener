package br.com.urlshortener.api.controller;

import br.com.urlshortener.data.ApiResponse;
import br.com.urlshortener.data.AuthToken;
import br.com.urlshortener.domain.model.Token;
import br.com.urlshortener.domain.model.User;
import br.com.urlshortener.domain.repository.TokenRepository;
import br.com.urlshortener.domain.repository.UserRepository;
import br.com.urlshortener.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/token")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@GetMapping("/generate")
	public ResponseEntity<ApiResponse> authenticate(
			@RequestHeader(value = "Authentication") String encodedAuthentication) throws UnsupportedEncodingException {
		final byte[] authenticationBytes = Base64Utils.decodeFromString(encodedAuthentication);
		final String[] decodedAuthentication = new String(authenticationBytes, "UTF-8").split(":");
		final String name = decodedAuthentication[0];
		final String password = decodedAuthentication[1];

		log.info("[AUTH] :: Autenticando o usuário: " + name);

		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(name,
				password);

		final Authentication authentication = authenticationManager.authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final User user = userRepository.findByLogin(name);

		final String jwtToken = jwtTokenUtil.generateToken(user);

		Token token = tokenRepository.findByUserId(user.getId());
		if (token == null) {
			token = new Token();
			token.setUserId(user.getId());
		}
		token.setAccessToken(jwtToken);
		token.setLogDate(new Timestamp(System.currentTimeMillis()));

		tokenRepository.save(token);

		final AuthToken authToken = new AuthToken(jwtToken, user.getLogin(), user.getId());

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.of("Acesso concedido", authToken));

	}

}
