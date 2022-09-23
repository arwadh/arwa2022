package com.caisse.projet.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Utilisateur",
uniqueConstraints = { 
		@UniqueConstraint(columnNames = "username"
				+ ""),
		@UniqueConstraint(columnNames = "email") 
	})
public class User {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
		  @NotBlank
		@Size(max = 40)
	  private String username;
	  @NotBlank
	  @Size(max = 60)
	  @Email
	  private String email;
	  private String nom;
	  private int code;
	  private String password;
	  private String reset=null;
	  private String role;
	  private String fileName;
	  private String  hometown;
	  private String  interests;
	  private String  experience;
	  private String  status;
	  private String  gender;
	  private String  phone;
	  private String  nationality;
	  private String language;
	  private String  lastjobexp;
	  private String  lastDesignation;
	  private String  department;
	  private String  reason;
	  private String  currentLocation;
	  private String industry;
	  
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

}
