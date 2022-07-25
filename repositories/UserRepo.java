package com.shuja.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shuja.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	
	@Query(value="Select * FROM user WHERE username LIKE %?1% OR email LIKE %?2%",nativeQuery = true)
	public List<User> searchUser(String username,String email);
}
