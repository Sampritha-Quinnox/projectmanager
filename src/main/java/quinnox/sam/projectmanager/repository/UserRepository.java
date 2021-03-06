package quinnox.sam.projectmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import quinnox.sam.projectmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	//@Query("SELECT u FROM User u WHERE u.username = :username")
			public quinnox.sam.projectmanager.entity.User getUserByUsername(@Param("username") String username);



}
