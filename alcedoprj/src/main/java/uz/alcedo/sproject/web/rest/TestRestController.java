package uz.alcedo.sproject.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import uz.alcedo.sproject.model.Test;
import uz.alcedo.sproject.service.TestService;

@RestController
@CrossOrigin(origins = "*")  // "*" Havfli! Aniq hostni ko'rsatish kerak
@RequestMapping("/api/v1/test")
public class TestRestController {
	
	private final TestService testService;
	
	@Autowired
	public TestRestController(TestService testService) {
		this.testService = testService;
	}

	@GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
		testService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/save")
    public ResponseEntity<?> update(@RequestBody Test test) {
        
		Test updatedTest = testService.save(test);       
        return new ResponseEntity<>(updatedTest, HttpStatus.OK);
        
    }	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable long id) {
		Test test = testService.getById(id);
		if (test == null) {
			Map<Object, Object> response = new HashMap<>();
			response.put("warn", "Test topilmadi");
			response.put("msg", "Bunday ID-li Test topilmadi");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
		}
		return new ResponseEntity<>(test, HttpStatus.OK);
	}

	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, 
									@RequestParam(defaultValue = "5") int size) {
		Pageable pageParam = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		Page<Test> pTests = testService.getAll(pageParam);
		Map<Object, Object> response = new HashMap<>();
		response.put("tests", pTests.getContent());
		response.put("currentPage", pTests.getNumber());
		response.put("totalPages", pTests.getTotalPages());
		response.put("totalElements", pTests.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/get-all-by-crit")
	public ResponseEntity<?> getAllByCrit(@RequestParam(defaultValue = "true") String filterCurrentUserTest,
										  @RequestParam(defaultValue = "") String filterNomi,
										  @RequestParam(defaultValue = "") String filterFani,
										  @RequestParam(defaultValue = "") String filterRazdel,
										  @RequestParam(defaultValue = "0") int page, 
										  @RequestParam(defaultValue = "5") int size) {
		Pageable pageParam = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		Page<Test> pTests = testService.getAllByCritNomiFaniRazdel(filterCurrentUserTest.startsWith("true"), 
																   filterNomi, filterFani, filterRazdel, 
																   pageParam);
		Map<Object, Object> response = new HashMap<>();
		response.put("tests", pTests.getContent());
		response.put("currentPage", pTests.getNumber());
		response.put("totalPages", pTests.getTotalPages());
		response.put("totalElements", pTests.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
