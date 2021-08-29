package uz.alcedo.sproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import uz.alcedo.sproject.model.Natija;

public interface NatijaRepository extends JpaRepository<Natija, Long>, JpaSpecificationExecutor<Natija> {

}
