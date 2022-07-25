package com.shuja.blog.services;

import java.util.List;
import java.util.Set;

import com.shuja.blog.payloads.UserDto;
import com.shuja.blog.payloads.UserResponseDto;


public interface UserService {
	
	UserResponseDto createUser(UserDto user);
	
	UserResponseDto updateUser(UserDto user,Integer userId);
	
	UserResponseDto getUserById(Integer userId);
	
	Set<UserResponseDto> getFollowers(Integer userId);
	
	Set<UserResponseDto> getFollowing(Integer UserId);
	
	Set<UserResponseDto> getBlocked(Integer userId);
	
	Set<UserResponseDto> follow(Integer userId,Integer followeeId);
	
	Set<UserResponseDto> block(Integer userId,Integer blockedId);
	
	List<UserResponseDto> getAllUsers();
	
	List<UserResponseDto> searchUsers(String username,String email);
	
	void deleteUser(Integer userId);
	
	void unFollow(Integer userId,Integer followeeId);
	
	void unBlock(Integer userId,Integer blockedId);
}

