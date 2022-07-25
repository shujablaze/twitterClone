package com.shuja.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,unique = true)
	private String username;
	
	@Column(nullable = false,unique = true)
	private String email;
	
	private char[] password;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date joinedOn;
	
	@ManyToMany
	private Set<User> followers = new HashSet<>();
	
	@ManyToMany(mappedBy = "followers")
	private Set<User> following = new HashSet<>();
	
	@ManyToMany
	private Set<User> blocked = new HashSet<>();
	
	@OneToMany(mappedBy="author",cascade = CascadeType.REMOVE)
	private Set<Tweet> tweets = new HashSet<>();
}
