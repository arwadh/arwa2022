package com.caisse.projet.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caisse.projet.Model.Job;
import com.caisse.projet.Model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

	List<User> findAllByEmail(String email);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	@Query(value = "SELECT * FROM utilisateur u JOIN job_users ju on u.id=ju.users_id JOIN utilisateur_jobs jus on jus.jobs_id=ju.job_id where jus.user_id=:iduser",nativeQuery=true)
	public List <User> getUsersAppliedByCompany(@Param("iduser") Long iduser);
}
