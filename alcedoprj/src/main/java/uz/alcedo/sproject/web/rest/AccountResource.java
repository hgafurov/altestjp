package uz.alcedo.sproject.web.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uz.alcedo.sproject.model.Role;
import uz.alcedo.sproject.model.User;
import uz.alcedo.sproject.service.UserService;
import uz.alcedo.sproject.service.dto.UserDTO;
import uz.alcedo.sproject.service.mapper.UserMapper;
import uz.alcedo.sproject.web.rest.vm.ManagedUserVM;
import uz.alcedo.sproject.web.rest.vm.UpdateUserVM;


@RestController
@CrossOrigin(origins = "*")  // "*" Havfli! Aniq hostni ko'rsatish kerak
@RequestMapping("/api/v1/auth")
public class AccountResource {
	
	private final UserService userService;	
	private final UserMapper userMapper;
	
	public AccountResource(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}
	
	@GetMapping("users")
	public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page, 
										 @RequestParam(defaultValue = "10") int size) {
		Pageable pageParam = PageRequest.of(page, size);
		Page<User> pUsers = userService.getAll(pageParam);
		Map<Object, Object> response = new HashMap<>();
		response.put("users", pUsers.getContent());
		response.put("currentPage", pUsers.getNumber());
		response.put("totalPages", pUsers.getTotalPages());
		response.put("totalElements", pUsers.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("get-user-by-login/{login}")
	public ResponseEntity<?> getUserByLogin(@PathVariable String login) {
		
		User user = userService.getByLogin(login);
		if (user == null) {
			Map<Object, Object> response = new HashMap<>();
			response.put("warn", "User topilmadi");
			response.put("msg", "Bunday login-li foydalanuvchi topilmadi");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
		}
		UserDTO userDTO = userMapper.userToUserDTO(user);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	@GetMapping("user/{id}")
	public ResponseEntity<?> getUserByID(@PathVariable long id) {
		
		User user = userService.getById(id);
		if (user == null) {
			Map<Object, Object> response = new HashMap<>();
			response.put("warn", "User topilmadi");
			response.put("msg", "Bunday ID-li foydalanuvchi topilmadi");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
		}
		UserDTO userDTO = userMapper.userToUserDTO(user);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	@PostMapping("register")
    public ResponseEntity<?> registerAccount(@RequestBody ManagedUserVM managedUserVM) {
		System.out.println(" =============== " + managedUserVM.getLogin() + "====================");
        if (!checkPasswordLength(managedUserVM.getPassword())) {
			Map<Object, Object> response = new HashMap<>();
			response.put("error", "Invalid password");
			response.put("msg", "Parol talabga javob bermaydi.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (!userService.checkLogin(managedUserVM.getLogin())) {
        	Map<Object, Object> response = new HashMap<>();
			response.put("error", "Invalid login");
			response.put("msg", "Bunday nomli login band");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = userMapper.userToUserDTO(userService.registerUser(managedUserVM, managedUserVM.getPassword()));
        
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
	
	@PostMapping("user/save")
    public ResponseEntity<?> update(@RequestBody UpdateUserVM updateUserVM) {
        
		User user = userService.getById(updateUserVM.getId());
        
        user.setIsmi(updateUserVM.getIsmi());
        user.setFamiliya(updateUserVM.getFamiliya());
        user.setOtaIsmi(updateUserVM.getOtaIsmi());
        user.setEmail(updateUserVM.getEmail());
        user.setTelNum(updateUserVM.getTelNum());
        user.setTugSana(updateUserVM.getTugSana());
        user.setActivated(updateUserVM.isActivated());
        user.setRoles(updateUserVM.getRoles().stream().map(stRole -> new Role(stRole)).collect(Collectors.toSet()));
        
        UserDTO userDTO = userMapper.userToUserDTO(userService.save(user));
        
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
	
	@PostMapping("hello")
    public ResponseEntity<?> hello() {
		System.out.println(" =============== "  + "====================");
    
		Map<Object, Object> response = new HashMap<>();
		response.put("error", "Invalid password");
		response.put("msg", "Parol talabga javob bermaydi.");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	
	@GetMapping("user/delete-by-login/{login}")
    public ResponseEntity<?> deleteByLogin(@PathVariable String login) {
		userService.deleteByLogin(login);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping("user/delete-by-id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
		userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	// ******* Static metodlar **************
	
    private static boolean checkPasswordLength(String password) {
        return !isEmptyString(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
    
	public static boolean isEmptyString(Object str) {
		return (str == null || "".equals(str));
	}
}
