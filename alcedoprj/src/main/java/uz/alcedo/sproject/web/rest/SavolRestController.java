package uz.alcedo.sproject.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import uz.alcedo.sproject.model.Savol;
import uz.alcedo.sproject.service.SavolService;

@RestController
@CrossOrigin(origins = "*")  // "*" Havfli! Aniq hostni ko'rsatish kerak
@RequestMapping("/api/v1/savol")
public class SavolRestController {
	
	private final SavolService savolService;
	
	public SavolRestController(SavolService savolService) {
		this.savolService = savolService;
	}
	
	@GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
		savolService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping("/save")
    public ResponseEntity<?> update(@RequestBody Savol savol) {
        
		Savol updatedSavol = savolService.save(savol);       
        return new ResponseEntity<>(updatedSavol, HttpStatus.OK);
        
    }	
	
	@PostMapping("/get-by-array")
    public ResponseEntity<?> getByArray(@RequestBody long[] ids) {
        List<Savol> savols = new ArrayList<>();
		for (long id : ids) {
			savols.add(savolService.getById(id));
			System.out.println("+++++ " + id);
		}
        return new ResponseEntity<>(savols, HttpStatus.OK);
        
    }	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable long id) {
		
		Savol savol = savolService.getById(id);
		if (savol == null) {
			Map<Object, Object> response = new HashMap<>();
			response.put("warn", "Savol topilmadi");
			response.put("msg", "Bunday ID-li Savol topilmadi");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
		}
		return new ResponseEntity<>(savol, HttpStatus.OK);
	}

	@GetMapping("/get-all")
	public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, 
										 @RequestParam(defaultValue = "10") int size) {
		Pageable pageParam = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		Page<Savol> pSavols = savolService.getAll(pageParam);
		Map<Object, Object> response = new HashMap<>();
		response.put("savols", pSavols.getContent());
		response.put("currentPage", pSavols.getNumber());
		response.put("totalPages", pSavols.getTotalPages());
		response.put("totalElements", pSavols.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/get-all-by-crit")
	public ResponseEntity<?> getAllByCrit(@RequestParam(defaultValue = "false") String filterCurrentUserSavols,
										  @RequestParam(defaultValue = "") String filterFani,
										  @RequestParam(defaultValue = "") String filterRazdel,
										  @RequestParam(defaultValue = "0") int page, 
										  @RequestParam(defaultValue = "5") int size) {
		System.out.println(filterCurrentUserSavols);
		Pageable pageParam = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		Page<Savol> pSavols = savolService.getAllByCriteria(filterCurrentUserSavols.endsWith("true"), filterFani, filterRazdel, pageParam);
		Map<Object, Object> response = new HashMap<>();
		response.put("savols", pSavols.getContent());
		response.put("currentPage", pSavols.getNumber());
		response.put("totalPages", pSavols.getTotalPages());
		response.put("totalElements", pSavols.getTotalElements());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
