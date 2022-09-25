package com.caisse.projet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.caisse.projet.Model.Cv;
import com.caisse.projet.Model.Job;


@Repository
public interface CvRepository  extends JpaRepository<Cv, Long> {
	List <Job> findAllByLibelleContaining(String libelle);
	@Query(value = "SELECT count(code) FROM Cv")
	public int nbre();

	@Query(value = "SELECT max(code) FROM Cv")
	public int max();
	@Query(value = "SELECT user_id FROM cv  WHERE 1",nativeQuery=true)
	public List<Long> getall();
}
