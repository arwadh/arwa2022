package com.caisse.projet.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.caisse.projet.Model.Cv;
import com.caisse.projet.Model.Job;
import com.caisse.projet.Model.User;
import com.caisse.projet.Repository.CvRepository;
import com.caisse.projet.Service.CvService;
import com.caisse.projet.Service.WordNet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")

public class CvController {
	@Autowired
	CvService service;

	@Autowired
	WordNet wordnet;
	 @GetMapping("/cv/7")
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
	 		 return service.max();
	 	 }
	    
	 }
	 @GetMapping("/cv")
		public List<Cv> getAll(){
			return service.getAll();
		}
	 @GetMapping("/all")
		public List<Long> get(){
			return service.getall();
		}
		@PostMapping("/cv/{id}")
	   public void save(@RequestParam("cv")  String cv,@RequestParam("file") MultipartFile file,@PathVariable("id") long id) throws IllegalStateException, IOException {
		Cv cvv = new ObjectMapper().readValue(cv, Cv.class);
			  try {
				  
				   service.addVisa(cvv, file, id);
				 
				    file.transferTo(new File("D:\\UploadFiles"+file.getOriginalFilename()));
				      
				    
				    } 
			  catch (Exception e) {
				      String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				     
				    }
		
	
		    
		
		  //service.savee(cvv,id);
	   }
		 @GetMapping("/cv/liste/{id}")
	public   Map<String, Double> test(@PathVariable("id") long id,@RequestParam("word1") List<String> word1, @RequestParam("word2") List<String>word2) {
			
			 //Double s=wordnet.test(word1, word2);
			// double d=(double)Math.round(s * 100.0d) / 100.0d;
			 
		//return s;
			return  wordnet.test(id,word1, word2);
	}
}
