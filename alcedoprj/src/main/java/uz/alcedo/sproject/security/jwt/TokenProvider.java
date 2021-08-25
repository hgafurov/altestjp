package uz.alcedo.sproject.security.jwt;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import uz.alcedo.sproject.security.UserNotActivatedException;

@Component
public class TokenProvider implements InitializingBean {

	private static final String AUTHORITIES_KEY = "roles";

	@Value("${jwt.secret}")
	private String secret;
	
    @Value("${jwt.expired}")
    private long tokenValidityInMilliseconds;
    
	@Override
	public void afterPropertiesSet() throws Exception {
    	secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
    
    public String createToken(Authentication authentication) {
    	String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    	long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validity)
                .compact();
    }
    
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
    
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (IllegalArgumentException e) {
        	throw new UserNotActivatedException("JWT token is expired or invalid");
        }
    }
}
