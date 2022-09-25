package com.caisse.projet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.caisse.projet.Model.Job;



@Repository
public interface JobRepository  extends JpaRepository<Job, Long> {

	List <Job> findAllByLibelleContaining(String libelle);
	@Query(value = "SELECT count(code) FROM Job")
	public int nbre();

	@Query(value = "SELECT max(code) FROM Job")
	public int max();
	
	@Query(value = "SELECT * FROM job j join job_users ju on j.id=ju.job_id WHERE ju.users_id=:iduser",nativeQuery=true)
	public List <Job> getAppliedJobByUser(@Param("iduser") Long iduser);
}
