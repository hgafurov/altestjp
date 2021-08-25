package uz.alcedo.sproject.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import uz.alcedo.sproject.model.User;
import uz.alcedo.sproject.repository.UserRepository;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return userRepository.findOneByLogin(login)
				.map(user -> createSpringSecurityUser(login, user))
				.orElseThrow(() -> new UsernameNotFoundException(login + " nomli foydalanuvchi ma'lumotlar bazasida yo'q"));
	}

	private org.springframework.security.core.userdetails.User createSpringSecurityUser(String login, User user) {
       
        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
        
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
            user.getPassword(),
            grantedAuthorities);
    }
}
