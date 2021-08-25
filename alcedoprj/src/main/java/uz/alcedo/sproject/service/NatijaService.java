package uz.alcedo.sproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.alcedo.sproject.model.Natija;
import uz.alcedo.sproject.repository.NatijaRepository;

@Service
public class NatijaService {
	
	private final NatijaRepository natijaRepository;
	
	@Autowired
	public NatijaService(NatijaRepository natijaRepository) {
		this.natijaRepository = natijaRepository;
	}

	public void delete(Long id) {
		natijaRepository.deleteById(id);
	}
	
	public Natija save(Natija natija) {
		return natijaRepository.save(natija);
	}
}
