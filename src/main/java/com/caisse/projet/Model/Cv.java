package com.caisse.projet.Model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

import javax.mail.Multipart;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "Cv")
public class Cv {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
private String code;
private String libelle;
private String doc;
private String intersts;
private String companyName;
private String job;
private String name;
private Double similarity;
@JsonIgnore
@OneToOne()
private User user;


}
