package com.caisse.projet.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.caisse.projet.Model.Job;
import com.caisse.projet.Model.User;
import com.caisse.projet.Repository.JobRepository;
import com.caisse.projet.Repository.UserRepository;

@Service
@Transactional
public class JobService {
	 @Autowired
		JobRepository repository;
	 @Autowired
		UserRepository repo;
	 
		public List<Job> getAll() {
			System.out.println("Get all Destinations 11111...");
	    	return repository.findAll(Sort.by("libelle").ascending());	    	
	    }
		public List<Job> getJobsByUser(long iduser) {
			System.out.println("Get all Destinations 11111...");
			User u = repo.findById(iduser).orElse(null);
			List<Job> jobs=  u.getJobs();
			return  jobs;
	    }
		
		public int max() {
			return repository.max();
		}
		
		public int nbre() {
			return repository.nbre();
		}
		
	    public Optional<Job> findById(Long id) {
	        return repository.findById(id);
	    }
	    
	    public void save(Job job,long iduser ) {
	    	System.out.println("save  all Destinations 11111...");
	    	 //LocalDateTime date = LocalDateTime.now();
	    	
	    	LocalDateTime localDate = LocalDateTime.now();
			System.out.println(localDate);
			 job.setJobDate(localDate);
			 job.setLibelle(job.getCompanyName());
	    	User u = repo.findById(iduser).orElse(null);
	    	u.getJobs().add(job);
	         repository.save(job);
	                             
	    }
	    public void update(Long id, Job job) {
	        
	            repository.save(job);
	        }
	    
	  
	
	    public List<Job> findByLibelle(String libelle) {
	        return repository.findAllByLibelleContaining(libelle);
	    }

	    public void delete(Long id) {
	        Optional<Job> cat = repository.findById(id);
	        cat.ifPresent(repository::delete);
	    }
	    public void assginemployeeTojob(Long iduser,Long idjob) {
	    	User u = repo.findById(iduser).orElse(null);
			Job j = repository.findById(idjob).orElse(null);
			j.setHide(true);
		
			//u.getJobs().add(j);
			j.getUsers().add(u);
	    }
	    public void favoris(Long idjob,int favoris) {
	    	System.out.println("save  all Destinations 11111...");
	    
	    	Job j = repository.findById(idjob).orElse(null);
	    	j.setFavoris(favoris);
	    	//j.setFavoris(2);
	    	
	    }
	    public List<Job> getappliedJobs(long iduser) {
	    	User u =repo.findById(iduser).orElse(null);
	    			return u.getJobs();
	    }
}
