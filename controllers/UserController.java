package com.shuja.blog.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shuja.blog.payloads.UserDto;
import com.shuja.blog.payloads.UserResponseDto;
import com.shuja.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	ResponseEntity<List<UserResponseDto>> getAllUsers(){
		List<UserResponseDto> users = userService.getAllUsers();
		
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	@PostMapping("/")
	ResponseEntity<UserResponseDto> createuser(@RequestBody UserDto userDto) {
		UserResponseDto user = userService.createUser(userDto);

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	ResponseEntity<UserResponseDto> getUser(@PathVariable Integer userId){
		UserResponseDto user = userService.getUserById(userId);
		
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	ResponseEntity<UserResponseDto> updateUser(@RequestBody UserDto userDto,@PathVariable Integer userId){
		UserResponseDto user = userService.updateUser(userDto, userId);
		
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	void deleteUser(@PathVariable Integer userId) {
		userService.deleteUser(userId);
	}
	
	@GetMapping("/{userId}/followers")
	ResponseEntity<Set<UserResponseDto>> getFollowers(@PathVariable Integer userId){
		Set<UserResponseDto> followers = userService.getFollowers(userId);
		
		return new ResponseEntity<Set<UserResponseDto>>(followers,HttpStatus.OK);
	}
	
	
	@PostMapping("/{userId}/followers")
	ResponseEntity<Set<UserResponseDto>> follow(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		Set<UserResponseDto> followers = userService.follow(userId, req.get("id"));
		
		return new ResponseEntity<>(followers,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{userId}/followers")
	void unfollow(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		userService.unFollow(userId, req.get("id"));
	}
	
	@GetMapping("/{userId}/block")
	ResponseEntity<Set<UserResponseDto>> getBlockedUsers(@PathVariable Integer userId){
		Set<UserResponseDto> blocked = userService.getBlocked(userId);
		
		return new ResponseEntity<Set<UserResponseDto>>(blocked,HttpStatus.OK);
	}
	
	@PostMapping("/{userId}/block")
	ResponseEntity<Set<UserResponseDto>> block(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		Set<UserResponseDto> blocked = userService.block(userId, req.get("id"));
		
		return new ResponseEntity<>(blocked,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{userId}/block")
	void unBlock(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		userService.unBlock(userId, req.get("id"));
	}
	
	@GetMapping("/{userId}/following")
	ResponseEntity<Set<UserResponseDto>> getFollowing(@PathVariable Integer userId){
		Set<UserResponseDto> following = userService.getFollowing(userId);
		
		return new ResponseEntity<Set<UserResponseDto>>(following,HttpStatus.OK);
	}
	
}
