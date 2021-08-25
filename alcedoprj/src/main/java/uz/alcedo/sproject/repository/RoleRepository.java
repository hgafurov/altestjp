package uz.alcedo.sproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uz.alcedo.sproject.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
