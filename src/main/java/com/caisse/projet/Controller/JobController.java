package com.caisse.projet.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caisse.projet.Model.Job;
import com.caisse.projet.Model.User;
import com.caisse.projet.Repository.JobRepository;
import com.caisse.projet.Service.JobService;
import com.caisse.projet.Service.UserService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class JobController {
	@Autowired
	JobService service;
	@Autowired
	UserService userService;
	@Autowired
	JobRepository repo;
	 @GetMapping("/job/7")
public  int getCode() {
	 System.out.println("Get Numbers...");
	 int  x = service.nbre();
	 System.out.println(x);
	 if (x == 0)
	 {
		 return 0;
	 }
	 else
	 {
		 System.out.println("oooo");
		 return service.max();
	 }
   
}
	@PostMapping("/job/{iduser}")
	public void save(@RequestBody Job job,@PathVariable("iduser")long iduser ) {
		
		//job.setJobDate(localDate);
		service.save(job, iduser);
	
	}
	@GetMapping("/job/Trips/getpostedjob/{iduser}")
	public  List<Job> getJobByRecruteur(@PathVariable("iduser")long iduser){
		return service.getJobsByUser(iduser);
	}
	@GetMapping("/job/Trips/listpay/{iduser}")
	public  List<Job> getJobsByUser(@PathVariable("iduser")long iduser)
	{
		
		return repo.getAppliedJobByUser(iduser);
	}

	@PutMapping("/job/jobfavoris/{iduser}/{favoris}")
	public void updateFavoris(@PathVariable("iduser") long id,@PathVariable("favoris") int favoris ){
	service.favoris(id,favoris);
		
		
	}
	@PutMapping("/job/employees/apply/{iduser}/{idjob}") 
		public void assginemployeeTojob(@PathVariable("iduser")Long iduser,@PathVariable("idjob")Long idjob) {
		service.assginemployeeTojob(iduser, idjob);
	}
	
	@GetMapping("/job")
	public List<Job> getAll(){
		return service.getAll();
	}
	/*  @GetMapping(path="/get/{id}")
	    public List<Job> getappliedJobs(@PathVariable("id") long iduser){
	    	return service.getappliedJobs(iduser);
	    }*/

}
