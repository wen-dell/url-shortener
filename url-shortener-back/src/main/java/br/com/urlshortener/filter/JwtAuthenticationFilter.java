package br.com.urlshortener.filter;

import br.com.urlshortener.domain.model.Token;
import br.com.urlshortener.domain.model.User;
import br.com.urlshortener.domain.repository.TokenRepository;
import br.com.urlshortener.domain.repository.UserRepository;
import br.com.urlshortener.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    final String header = request.getHeader(JwtTokenUtil.headerName);
    if (header == null || !header.startsWith(JwtTokenUtil.tokenPrefix)) {
      filterChain.doFilter(request, response);
      return;
    }

    final String accessToken = header.replace(JwtTokenUtil.tokenPrefix, "");
    String login = null;
    try {
      login = jwtTokenUtil.getLoginFromToken(accessToken);
    } catch (IllegalArgumentException error) {
      logger.error("[JWT] :: Não foi possível decodificar o login a partir do token: " + accessToken);
      filterChain.doFilter(request, response);
      return;
    } catch (ExpiredJwtException error) {
      logger.error("[JWT] :: O token informado pelo usuário " + login + " expirou, token: " + accessToken);
      filterChain.doFilter(request, response);
      return;
    } catch (SignatureException error) {
      logger.error("[JWT] :: Não foi possível autenticar o usuário " + login + ", token: " + accessToken);
      filterChain.doFilter(request, response);
      return;
    }

    final User user = userRepository.findByName(login);
    final Token token = tokenRepository.findByUserId(user.getId());

    if (!jwtTokenUtil.validateToken(accessToken, user.getName())) {
      logger.error("[JWT] :: O token utilizado pelo usuário " + login + " é invalido, token: " + accessToken);
      filterChain.doFilter(request, response);
      return;
    }

    final String ipAddress = request.getHeader("Ip-Address");

    if (token != null && !accessToken.equals(token.getAccessToken())) {
      logger.error("[JWT] :: O token utilizado pelo usuario " + login + " não é o mesmo persistido no banco, token: " + accessToken);
      filterChain.doFilter(request, response);
      return;
    }

    final String newToken = jwtTokenUtil.generateToken(user);

    response.setHeader(JwtTokenUtil.headerName, JwtTokenUtil.tokenPrefix + newToken);

    final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
            null, Arrays.asList(new SimpleGrantedAuthority("USER")));

    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}

