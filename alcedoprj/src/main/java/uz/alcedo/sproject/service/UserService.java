package uz.alcedo.sproject.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uz.alcedo.sproject.model.Role;
import uz.alcedo.sproject.model.User;
import uz.alcedo.sproject.repository.UserRepository;
import uz.alcedo.sproject.security.RolesConstants;
import uz.alcedo.sproject.service.dto.UserDTO;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}    
	
	public User registerUser(UserDTO userDTO, String password) {
		
		userRepository.findOneByLogin(userDTO.getLogin()).ifPresent(user -> {
			throw new BadCredentialsException("[" + userDTO.getLogin() + "] nomli login band");
		});
		
		User newUser = new User();
		newUser.setLogin(userDTO.getLogin());
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setPassword(encryptedPassword);
		newUser.setIsmi(userDTO.getIsmi());
		newUser.setFamiliya(userDTO.getFamiliya());
		newUser.setOtaIsmi(userDTO.getOtaIsmi());
		newUser.setEmail(userDTO.getEmail());
		newUser.setTelNum(userDTO.getTelNum());
		newUser.setTugSana(userDTO.getTugSana());
		newUser.setActivated(true); // ?????????????????????? hozircha !!!!!!!!!!!!
		Set<Role> roles = new HashSet<>();
		//roleRepository.findById(RolesConstants.USER).ifPresent(roles::add);
		Role role = new Role();
		role.setName(RolesConstants.USER);
		roles.add(role);
		newUser.setRoles(roles);
		
		return userRepository.save(newUser);
	}
		
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User Save(UserDTO userDTO, String password) {
		
		userRepository.findOneByLogin(userDTO.getLogin()).ifPresent(user -> {
			throw new BadCredentialsException("[" + userDTO.getLogin() + "] nomli login band");
		});
		
		User newUser = new User();
		newUser.setLogin(userDTO.getLogin());
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setPassword(encryptedPassword);
		newUser.setIsmi(userDTO.getIsmi());
		newUser.setFamiliya(userDTO.getFamiliya());
		newUser.setOtaIsmi(userDTO.getOtaIsmi());
		newUser.setEmail(userDTO.getEmail());
		newUser.setTelNum(userDTO.getTelNum());
		newUser.setTugSana(userDTO.getTugSana());
		newUser.setActivated(userDTO.isActivated()); 
		Set<Role> roles = new HashSet<>();
		//roleRepository.findById(RolesConstants.USER).ifPresent(roles::add);
		Role role = new Role();
		role.setName(RolesConstants.USER);
		roles.add(role);
		newUser.setRoles(roles);
		
		return userRepository.save(newUser);
	}

	public User getByLogin(String login) {
		return userRepository.findOneByLogin(login)
				.orElse(null);
	}
	
	public User getById(long id) {
		return userRepository.findOneById(id).orElse(null);
	}
	
	public Page<User> getAll(Pageable pagable) {
		return userRepository.findAll(pagable);
	}
		
    public void deleteByLogin(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
        });
    }
    
    public void deleteById(long id) {
        userRepository.findOneById(id).ifPresent(user -> {
            userRepository.delete(user);
        });
    }
    
    public boolean checkLogin(String login) {
    	if (userRepository.countLogin(login) != 0) {
    		return false;
    	}
    	return true;
    }
	
}
