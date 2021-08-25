package uz.alcedo.sproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uz.alcedo.sproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findOneByLogin(String login);
	Optional<User> findOneById(Long id);

	@Query("select count(u) from User u where u.login=?1")
	int countLogin(String login);  
}