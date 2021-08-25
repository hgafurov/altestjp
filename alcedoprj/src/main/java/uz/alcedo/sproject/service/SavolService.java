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
import org.springframework.transaction.annotation.Transactional;

import uz.alcedo.sproject.model.Savol;
import uz.alcedo.sproject.repository.SavolRepository;
import uz.alcedo.sproject.security.SecurityUtils;

@Service
@Transactional
public class SavolService {
	
	private final SavolRepository savolRepository;
	
	@Autowired
	public SavolService(SavolRepository savolRepository) {
		this.savolRepository = savolRepository;
	}

	public Savol getById(long id) {
		return savolRepository.findOneById(id).orElseThrow();
	}
	
	public Page<Savol> getAll(Pageable pagable) {
		return savolRepository.findAll(pagable);
	}
	
	public Page<Savol> getAllByCriteria(boolean filterCurrentUserSavols, String filterFani, String filterRazdel, Pageable pagable) {
		Specification<Savol> spec = new Specification<Savol>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Savol> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (filterCurrentUserSavols) {
					String currentUserName = SecurityUtils.getCurrentUserLogin().orElse(null);
					if (currentUserName != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("createdBy"), currentUserName)));
					}
				}
				if (filterFani != null && !filterFani.isBlank()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
						 	criteriaBuilder.lower(root.get("fani")), "%" + filterFani.toLowerCase() + "%")));
				}
				if (filterRazdel != null && !filterRazdel.isBlank()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
							criteriaBuilder.lower(root.get("razdel")), "%" + filterRazdel.toLowerCase() + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
		return savolRepository.findAll(spec, pagable);
	}
	
	public Savol save(Savol savol) {
		System.out.println(savol.getId()+savol.getFani());
		return savolRepository.save(savol);
	}
	
	public void delete(long id) {
		savolRepository.deleteById(id);
	}
}
