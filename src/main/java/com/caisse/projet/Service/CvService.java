package com.caisse.projet.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caisse.projet.Model.Cv;
import com.caisse.projet.Model.Job;
import com.caisse.projet.Model.User;
import com.caisse.projet.Repository.CvRepository;
import com.caisse.projet.Repository.JobRepository;
import com.caisse.projet.Repository.UserRepository;

@Service
@Transactional
public class CvService {
	 @Autowired
		CvRepository repository;
	 @Autowired
		UserRepository repo;
		public List<Cv> getAll() {
			System.out.println("Get all Destinations 11111...");
	    	return repository.findAll(Sort.by("libelle").ascending());	    	
	    }
		public int max() {
			return repository.max();
		}
		
		public int nbre() {
			return repository.nbre();
		}
		
		   public void savee(Cv cv,long iduser ) {
		    	System.out.println("save  all Destinations 11111...");
		    	User u = repo.findById(iduser).orElse(null);
		    	
		    	cv.setUser(u);
		    	 repository.save(cv);
		                             
		    }
		    public long save(Cv cv) {
		    	System.out.println("save  all Users 11111...");
		      
		        return repository.save(cv)
		                             .getId();
		    }
		    @Value("${app.upload.dir:${user.home}}")
		    public String uploadDirectory;

			private String uploadFile(MultipartFile uploadFile) {
			    String url = "";
			    try {
			     
			     url= Paths.get(uploadDirectory, uploadFile.getOriginalFilename()).toString();
			
			    } catch (Exception e) {
			      url = "Could not upload the file: " + uploadFile.getOriginalFilename() + "!";
			     
			    }
			    return url;
			  }
			
			public void addVisa(Cv cv,MultipartFile uploadFile, long iduser) throws IllegalStateException, IOException {
				
	           User u = repo.findById(iduser).orElse(null);
	         List<Long> ids=getall();
	          User uu=cv.getUser();
	      
	          if(ids.contains(iduser))
	          {
	        	 System.out.println("exist");
	          }
	          else {
	            	
	         System.out.println("hello");	
	         String url= uploadFile( uploadFile);
  		      cv.setDoc(url);
	    	cv.setUser(u);
	    	 repository.save(cv);}
	          
			}
			
			
			public List<Long> getall(){
				return repository.getall();
			}
}
