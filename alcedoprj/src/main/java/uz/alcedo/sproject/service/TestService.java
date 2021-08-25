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

import uz.alcedo.sproject.model.Test;
import uz.alcedo.sproject.repository.TestRepository;
import uz.alcedo.sproject.security.SecurityUtils;

@Service
@Transactional
public class TestService {

	private final TestRepository testRepository;
	
	@Autowired
	public TestService(TestRepository testRepository) {
		this.testRepository = testRepository;
	}
	
	public void delete(long id) {
		this.testRepository.deleteById(id);
	}
	
	public Test save(Test test) {
		return testRepository.save(test);
	}
	
	public Test getById(long id) {
		return testRepository.findById(id).orElse(null);
	}
	
	public Page<Test> getAll(Pageable pageable) {
		return testRepository.findAll(pageable);
	}
	
	// Kriteriya bo'yicha testlar
	public Page<Test> getAllByCritNomiFaniRazdel(boolean filterCurrentUserTests, String filterNomi, String filterFani, String filterRazdel, Pageable pageable) {
			
		Specification<Test> spec = new Specification<Test>() {

			private static final long serialVersionUID = 202107302053L;

			@Override
			public Predicate toPredicate(Root<Test> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (filterCurrentUserTests) {
					String currentUserName = SecurityUtils.getCurrentUserLogin().orElse(null);
					if (currentUserName != null) {
						predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("createdBy"), currentUserName)));
					}
				}
				if (filterNomi != null && !filterNomi.isBlank()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.like(
						    criteriaBuilder.lower(root.get("nomi")), "%" + filterNomi.toLowerCase() + "%")));
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
		return testRepository.findAll(spec, pageable);
	}
}
