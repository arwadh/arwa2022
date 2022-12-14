package com.caisse.projet.Controller;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.json.JsonParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.caisse.projet.Model.Job;
import com.caisse.projet.Model.User;
import com.caisse.projet.Repository.UserRepository;
import com.caisse.projet.Service.JobService;
import com.caisse.projet.Service.UserDetailsImpl;
import com.caisse.projet.Service.UserService;
import com.caisse.projet.config.JwtTokenUtil;
import com.caisse.projet.domain.JwtResponse;
import com.caisse.projet.domain.Message;
import com.caisse.projet.request.LoginRequest;
import com.caisse.projet.request.RegisterRequest;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {
	 @Autowired
	 private UserService userService;
	 @Autowired
	 private JobService service;
	 @Autowired  
	 ServletContext context;
	 @Autowired 
	 UserRepository UserRepository;
	 @Autowired
	
	 private AuthenticationManager authenticationManager;
	@Autowired 
	PasswordEncoder encoder;
	@Autowired 	
	JwtTokenUtil jwtUtils;
	 @GetMapping("/pwd")
	    public ResponseEntity<?> getPwd() throws Exception{
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(16);
		String encoderpassword=encoder.encode("aaaaaaaa");
		return ResponseEntity.ok(new Message(encoderpassword));
	 }
	   
	 @GetMapping("/users")
	    public List<User> list() {
		 System.out.println("Get all Users...");
	             return userService.getAll();
	   }
	 	 
	 @GetMapping("/users/{id}")
	 public ResponseEntity<User> post(@PathVariable long id) {
	        Optional<User> user = userService.findById(id);
	        return user.map(ResponseEntity::ok)
	                   .orElseGet(() -> ResponseEntity.notFound()
                                               .build());
	    }
	    
	 @GetMapping("/users/verif/{email}")
	    public List<User> listUser(@PathVariable String email) {
		 System.out.println("Get all Users...");
	             return userService.getAllByEmail(email);
	   }
	 
	 @GetMapping("/users/auth/{name}")
	 public ResponseEntity<User> login(@PathVariable String name) {
	        Optional<User> user = userService.login(name);
	        return user.map(ResponseEntity::ok)
	                   .orElseGet(() -> ResponseEntity.notFound()
                                               .build());
	    }
	 
	 @GetMapping("/users/resetPwd/{id}")
	 public String reset(@PathVariable long id) {
	        Optional<User> user = userService.findById(id);
	        return user.get().getReset();
	       
	    }
	 @RequestMapping(value="/authenticateC",method = RequestMethod.POST)
	 public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest data) {
			System.out.println("aaaa");
			System.out.println(data.getPassword());
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							data.getUsername(),
							data.getPassword())
				
					);
			  System.out.println("bbbbb");
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
			 System.out.println(jwt);
			 System.out.println(userDetails.getUsername());
			 System.out.println(userDetails.getRole());
			return ResponseEntity.ok(new JwtResponse(jwt, 
													 userDetails.getId(), 
													 userDetails.getUsername(), 
													 userDetails.getEmail(), 
													 userDetails.getRole()));
		}
	    
	 @PostMapping("/Rec")
	 public long createUser (@RequestParam("file") MultipartFile file,
			 @RequestParam("user") String user) throws JsonParseException , JsonMappingException , Exception
	 {
		 System.out.println("Save user.............");
	    User userr = new ObjectMapper().readValue(user, User.class);
	   userr.setReset(userr.getPassword());
	   userr.setPassword(encoder.encode(userr.getPassword()));
	  
	    boolean isExit = new File(context.getRealPath("/ImgUsers/")).exists();
	    if (!isExit)
	    {
	    	new File (context.getRealPath("/ImageUsers/")).mkdir();
	    	System.out.println("mk dir Imagess.............");
	    }
	    System.out.println("Save Article  22222.............");
	    String filename = file.getOriginalFilename();
	    String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
	    File serverFile = new File (context.getRealPath("/ImgUsers/"+File.separator+newFileName));
	    try
	    {
	    	System.out.println("Image");
	    	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
	    	 
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    System.out.println("Save Article 333333.............");
	    userr.setFilename(newFileName);
	   
	    userr.setRole("Candidat");
	    return userService.save(userr);
	 }
		@PostMapping("/u")
		public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
			if (UserRepository.existsByUsername(signUpRequest.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new Message("Error: Username is already taken!"));
			}

			if (UserRepository.existsByEmail(signUpRequest.getEmail())) {
				return ResponseEntity
						.badRequest()
						.body(new Message("Error: Email is already in use!"));
			}

			// Create new user's account
			/*User user = new User(signUpRequest.getUsername(), 
								 signUpRequest.getEmail(),
								 encoder.encode(signUpRequest.getPassword()),
										 signUpRequest.getRole()		 );*/
			//UserRepository.save(user);

			return ResponseEntity.ok(new Message("User registered successfully!"));
		}	
	 @PostMapping("/userss")
		public ResponseEntity<?> registerUser(@RequestParam("file") MultipartFile file,
				 @RequestParam("user") String user) throws JsonParseException , JsonMappingException , Exception{
		 User userr = new ObjectMapper().readValue(user, User.class);
			if (UserRepository.existsByUsername(userr.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new Message("Error: Username is already taken!"));
			}

			if (UserRepository.existsByEmail(userr.getEmail())) {
				return ResponseEntity
						.badRequest()
						.body(new Message("Error: Email is already in use!"));
			}

			
			    boolean isExit = new File(context.getRealPath("/ImgUsers/")).exists();
			    if (!isExit)
			    {
			    	new File (context.getRealPath("/ImgUsers/")).mkdir();
			    	System.out.println("mk dir Imagess.............");
			    }
			    System.out.println("Save Article  22222.............");
			    String filename = file.getOriginalFilename();
			    String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
			    File serverFile = new File (context.getRealPath("/ImgUsers/"+File.separator+newFileName));
			    try
			    {
			    	System.out.println("Image");
			    	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
			    	 
			    }catch(Exception e) {
			    	e.printStackTrace();
			    }
			    System.out.println("Save Article 333333.............");
			    userr.setFilename(newFileName);
			     userService.save(userr);

			return ResponseEntity.ok(new Message("User registered successfully!"));
		}	
	 @PostMapping("/recruiterregister")
	 public long createrecuiter (@RequestParam("file") MultipartFile file,
			 @RequestParam("user") String user) throws JsonParseException , JsonMappingException , Exception
	 {
		 System.out.println("Save user.............");
	    User userr = new ObjectMapper().readValue(user, User.class);
	    userr.setReset(userr.getPassword());
	    userr.setRole("Recruiter");
	    
	    userr.setPassword(encoder.encode(userr.getPassword()));
	    boolean isExit = new File(context.getRealPath("/ImgUsers/")).exists();
	    if (!isExit)
	    {
	    	new File (context.getRealPath("/ImgUsers/")).mkdir();
	    	System.out.println("mk dir Imagess.............");
	    }
	    System.out.println("Save Article  22222.............");
	    String filename = file.getOriginalFilename();
	    String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
	    File serverFile = new File (context.getRealPath("/ImgUsers/"+File.separator+newFileName));
	    try
	    {
	    	System.out.println("Image");
	    	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
	    	 
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    System.out.println("Save Article 333333.............");
	    userr.setFilename(newFileName);
	    userr.setRole("Recruteur");
	    return userService.save(userr);
	 }
	 
	    public long save(@RequestBody User User) {
		 System.out.println("Save all Users...");
	        return userService.save(User);
	    }

	/* @PutMapping("/users/{id}")
	    public void update(@PathVariable long id, @RequestBody User User) {
	        Optional<User> user = userService.findById(id);
	        if (user.isPresent()) {
	            userService.update(id, User);
	        } else {
	            userService.save(User);
	        }
	    }*/
		 @PutMapping("/users/{id}")
		    public void update(@PathVariable long id,@RequestParam("file") MultipartFile file,
					 @RequestParam("user") String user) throws JsonParseException , JsonMappingException , Exception {
		     User userr = new ObjectMapper().readValue(user, User.class);
		        	deleteUserImage(userr);
		        	 String filename = file.getOriginalFilename();
		     	    String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
		     	    userr.setFilename(newFileName);
		            userService.update(id, userr);
		           
		            addUserImage(file);
		       
		    }
		 private void addUserImage(MultipartFile file)
		    {
		    	boolean isExit = new File(context.getRealPath("/ImgUsers/")).exists();
			    if (!isExit)
			    {
			    	new File (context.getRealPath("/ImgUsers/")).mkdir();
			    	System.out.println("mk dir Imagess.............");
			    }
			    String filename = file.getOriginalFilename();
			    String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
			    File serverFile = new File (context.getRealPath("/ImgUsers/"+File.separator+newFileName));
			    try
			    {
			    
			    	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
			    	 
			    }catch(Exception e) {
			    	 System.out.println("Failed to Add Image User !!");
			    }
			    
		    	
		    }
		 private void deleteUserImage(User user)
		    {
		    	System.out.println( " Delete User Image");
		         try { 
		        	 File file = new File (context.getRealPath("/ImgUsers/"+user.getFilename()));
		        		System.out.println(file);
		             System.out.println(user.getFilename());
		              if(file.delete()) { 
		                System.out.println(file.getName() + " is deleted!");
		             } else {
		                System.out.println("Delete operation is failed.");
		                }
		          }
		            catch(Exception e)
		            {
		                System.out.println("Failed to Delete image !!");
		            }
		    }

	    @DeleteMapping("/users/{id}")
	    public void delete(@PathVariable ("id") long id) {
	        userService.delete(id);
	    }
	     
	    @GetMapping(path="/ImgUsers/{id}")
		 public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
			 User User   =userService.findById(id).get();
			 return Files.readAllBytes(Paths.get(context.getRealPath("/ImgUsers/")+User.getFilename()));
		 }

	    @GetMapping("/job/jobs/getUserByjob2/{iduser}")
	    public List<User> getallUsersByJobsApplied(@PathVariable("iduser") long id) {
	    	return UserRepository.getUsersAppliedByCompany(id);
	    }
}
