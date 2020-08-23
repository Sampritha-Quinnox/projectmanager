package quinnox.sam.projectmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import quinnox.sam.projectmanager.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	List<Project> findByname(String name);


}
