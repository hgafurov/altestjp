package uz.alcedo.sproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import uz.alcedo.sproject.security.jwt.JWTConfigurer;
import uz.alcedo.sproject.security.jwt.TokenProvider;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final TokenProvider tokenProvider;
	
	@Autowired
	public SecurityConfiguration(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.cors()
		.and()
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
				.antMatchers("/api/v1/files/**").permitAll()
				.antMatchers("/api/v1/auth/**").permitAll()
				.antMatchers("/api/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
		.and()
	        .apply(securityConfigurerAdapter());;
	}
	
	private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
