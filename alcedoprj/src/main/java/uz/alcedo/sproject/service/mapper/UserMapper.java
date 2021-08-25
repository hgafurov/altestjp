package uz.alcedo.sproject.service.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import uz.alcedo.sproject.model.Role;
import uz.alcedo.sproject.model.User;
import uz.alcedo.sproject.service.dto.UserDTO;

@Service
public class UserMapper {
	
    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }
    
	public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }
	
	
	public User userDTOToUser(UserDTO userDTO) {
		if (userDTO == null) {
            return null;
        } else {
        	User user = new User();
        	
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setIsmi(userDTO.getIsmi());
            user.setFamiliya(userDTO.getFamiliya());
            user.setOtaIsmi(userDTO.getOtaIsmi());
            user.setEmail(userDTO.getEmail());
            user.setTelNum(userDTO.getTelNum());
            user.setTugSana(userDTO.getTugSana());
            user.setActivated(userDTO.isActivated());
            Set<Role> roles = this.rolesFromStrings(userDTO.getRoles());
            user.setRoles(roles);
            
            return user;
        }
	}
	
    private Set<Role> rolesFromStrings(Set<String> rolesAsString) {
        
    	Set<Role> roles = new HashSet<>();

        if(rolesAsString != null){
            roles = rolesAsString.stream().map(string -> {
                Role role = new Role();
                role.setName(string);
                return role;
            }).collect(Collectors.toSet());
        }

        return roles;
    }
}
