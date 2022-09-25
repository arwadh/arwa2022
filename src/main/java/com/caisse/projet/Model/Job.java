package com.caisse.projet.Model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Job")

public class Job {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
private String code;
private String libelle;
private String jobRole;

private LocalDateTime jobDate;
private String jobExperience;
private String  skills;
private String jobDescription;
private String jobType;
private int favoris=0;
private String companyName;
private boolean hide=false;
@JsonIgnore
@ManyToMany()
private List<User> users;
}
