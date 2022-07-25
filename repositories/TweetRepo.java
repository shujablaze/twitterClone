package com.shuja.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shuja.blog.entities.Tweet;

public interface TweetRepo extends JpaRepository<Tweet,Integer> {
	
	@Query(value = "SELECT * FROM tweet WHERE author_id IN "
			+ "(SELECT following_id FROM user_followers WHERE followers_id = ?1) "
			+ "ORDER BY timestamp desc",nativeQuery = true)
	public List<Tweet> getTimeline(Integer userId);
}
