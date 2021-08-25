package uz.alcedo.sproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import uz.alcedo.sproject.model.Savol;


@Repository
public interface SavolRepository extends JpaRepository<Savol, Long>, JpaSpecificationExecutor<Savol> {

	Optional<Savol> findOneById(Long id);
}
