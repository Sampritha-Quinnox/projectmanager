package quinnox.sam.projectmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import quinnox.sam.projectmanager.entity.Applied;


public interface AppliedRepository extends JpaRepository<Applied, Long> {
	List<Applied> findByprojectname(String projectname);


}
