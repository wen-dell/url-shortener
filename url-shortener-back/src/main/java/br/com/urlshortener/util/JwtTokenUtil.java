package br.com.urlshortener.util;

import br.com.urlshortener.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    public static final int accessTokenValiditySeconds = 5000 * 60 * 60;
    public static final String signingKey = "#knSm0[-W4WX9GR.";
    public static final String tokenPrefix = "Bearer ";
    public static final String headerName = "Authorization";

    public String getLoginFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String tokenBuilder(User user) {
        final Claims claims = Jwts.claims().setSubject(user.getLogin());        

        final Date issuedAt = new Date(System.currentTimeMillis());
        final Date expiration = new Date(System.currentTimeMillis() + accessTokenValiditySeconds * 1000);
        return Jwts.builder().setClaims(claims).claim("userId", user.getId()).setIssuer("http://localhost:8080").setIssuedAt(issuedAt)
                .setExpiration(expiration).signWith(SignatureAlgorithm.HS256, signingKey).compact();
    }

    public String generateToken(User user) {
        return tokenBuilder(user);
    }

    public Boolean validateToken(String token, String login) {
        if (isTokenExpired(token)) {
            return false;
        }

        final String loginFromToken = getLoginFromToken(token);

        if (!loginFromToken.equals(login)) {
            return false;
        }

        return true;
    }


}
