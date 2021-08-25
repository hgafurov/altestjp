package uz.alcedo.sproject.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
