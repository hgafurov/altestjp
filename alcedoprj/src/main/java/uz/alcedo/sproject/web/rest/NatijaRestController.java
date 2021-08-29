package uz.alcedo.sproject.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uz.alcedo.sproject.model.Natija;
import uz.alcedo.sproject.service.NatijaService;
import uz.alcedo.sproject.service.TestService;
import uz.alcedo.sproject.service.UserService;
import uz.alcedo.sproject.web.rest.vm.NatijaVM;

@RestController
@CrossOrigin(origins = "*")  // "*" Havfli! Aniq hostni ko'rsatish kerak
@RequestMapping("/api/v1/natija")
public class NatijaRestController {
	
	private final NatijaService natijaService;
	private final TestService testService;
	private final UserService userService;
	
	public NatijaRestController(NatijaService natijaService, TestService testService, UserService userService) {
		this.natijaService = natijaService;
		this.testService = testService;
		this.userService = userService; 
	}

	@PostMapping("/save")
    public ResponseEntity<?> update(@RequestBody NatijaVM natijaVM) {
		System.out.println("************" + natijaVM.getUsername());
        Natija natija = new Natija();
        natija.setSavolCount(natijaVM.getSavolCount());
        natija.setTjCount(natijaVM.getTjCount());
        natija.setUser(userService.getByLogin(natijaVM.getUsername()));
        natija.setTest(testService.getById(natijaVM.getTestId()));
		natija = natijaService.save(natija);    
        return new ResponseEntity<>(natija, HttpStatus.OK);
    }
	
	@GetMapping("/get-all-by-crit")
	public ResponseEntity<?> getAllByCrit(@RequestParam(defaultValue = "true") String filterCurrentUser,
										  @RequestParam(defaultValue = "0") int page, 
										  @RequestParam(defaultValue = "5") int size) {
		
		Pageable pageParam = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		Page<Natija> pNatijas = natijaService.getAllByCrit(filterCurrentUser.startsWith("true"), 
														   pageParam);
		Map<Object, Object> response = new HashMap<>();
		response.put("natija", pNatijas.getContent());
		response.put("currentPage", pNatijas.getNumber());
		response.put("totalPages", pNatijas.getTotalPages());
		response.put("totalElements", pNatijas.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
