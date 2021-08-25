package uz.alcedo.sproject.web.rest;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import uz.alcedo.sproject.model.Role;
import uz.alcedo.sproject.model.User;
import uz.alcedo.sproject.security.jwt.JWTFilter;
import uz.alcedo.sproject.security.jwt.TokenProvider;
import uz.alcedo.sproject.service.UserService;
import uz.alcedo.sproject.web.rest.vm.LoginVM;

@RestController
@CrossOrigin(origins = "*")  // "*" Havfli! Aniq hostni ko'rsatish kerak
@RequestMapping("/api/v1/auth")
public class UserJWTController {
	
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final UserService userService;
	
	public UserJWTController(TokenProvider tokenProvider, 
			AuthenticationManagerBuilder authenticationManagerBuilder,
			UserService userService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userService = userService;
	}
	
	/**
	 * @param loginVM
	 * 			JSON example
	 * 			{
	 * 				"username": "user",
	 * 				"password": "125456"
	 * 			}
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<JWTToken> login(@RequestBody LoginVM loginVM) {
		
		System.out.println(loginVM.getUsername() + " [1] " + loginVM.getPassword());
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
		System.out.println(loginVM.getUsername() + " [1] " + loginVM.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication);
		System.out.println(loginVM.getUsername() + " [2] " + loginVM.getPassword());
		HttpHeaders header = new HttpHeaders();
		header.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		JWTToken jwtToken = new JWTToken(jwt, userService.getByLogin(loginVM.getUsername()));
		System.out.println(jwtToken.username);
		return new ResponseEntity<>(jwtToken, header, HttpStatus.OK);
	}
	
	/**
     * Tokenni qaytarish uchun statik klass
     */
	static class JWTToken {
		private String ismi;
		private String familiya;
		private String username;
		private String roles;
        private String token;

        JWTToken(String token, User user) {
            this.token = token;
            this.ismi = user.getIsmi();
            this.familiya =user.getFamiliya();
            this.username = user.getLogin();
            this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(", "));
        }

        @JsonProperty("ismi")
        public String getIsmi() {
			return ismi;
		}


		public void setIsmi(String ismi) {
			this.ismi = ismi;
		}

		@JsonProperty("familiya")
		public String getFamiliya() {
			return familiya;
		}


		public void setFamiliya(String familiya) {
			this.familiya = familiya;
		}

		@JsonProperty("username")
		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}

		@JsonProperty("roles")
		public String getRoles() {
			return roles;
		}

		public void setRoles(String roles) {
			this.roles = roles;
		}

		@JsonProperty("token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }
    }

}
