package com.shuja.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tweet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String text;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@ManyToOne
	private Tweet replyTo;
	
	@OneToMany(mappedBy="replyTo")
	private Set<Tweet> reply = new HashSet<>();
	
	@ManyToMany
	private Set<User> likes = new HashSet<>();
	
	@ManyToMany
	private Set<User> retweets = new HashSet<>();
	
	@ManyToMany(mappedBy = "tweets")
	private Set<Tag> tags = new HashSet<>();
	
	@ManyToOne
	private User author;
	
	@PreRemove
	private void freeReplies() {
		reply.stream().forEach(reply -> reply.setReplyTo(null));
	}
}
