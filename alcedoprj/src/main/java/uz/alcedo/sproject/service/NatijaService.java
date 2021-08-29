package uz.alcedo.sproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import uz.alcedo.sproject.model.Natija;
import uz.alcedo.sproject.repository.NatijaRepository;
import uz.alcedo.sproject.security.SecurityUtils;

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
	
	public Page<Natija> getAllByCrit(boolean filterIsCurrentUser, Pageable pageable) {
		
		Specification<Natija> spec = new Specification<Natija>() {

			private static final long serialVersionUID = -2320846698238710816L;

			@Override
			public Predicate toPredicate(Root<Natija> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (filterIsCurrentUser) {
					String currentUserName = SecurityUtils.getCurrentUserLogin().orElse(null);
					if (currentUserName != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("createdBy"), currentUserName)));
					}
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return natijaRepository.findAll(spec, pageable);
	}
}
