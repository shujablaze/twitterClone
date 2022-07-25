package com.shuja.blog.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuja.blog.entities.User;
import com.shuja.blog.exceptions.ResourceNotFoundException;
import com.shuja.blog.payloads.UserDto;
import com.shuja.blog.payloads.UserResponseDto;
import com.shuja.blog.repositories.UserRepo;
import com.shuja.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;

	@Autowired
	ModelMapper mapper;

	@Override
	public UserResponseDto createUser(UserDto user) {
		User userEntity = new User();

		userEntity.setUsername(user.getUsername());
		userEntity.setEmail(user.getEmail());
		userEntity.setPassword(user.getPassword());

		User newUser = userRepo.save(userEntity);

		return mapper.map(newUser, UserResponseDto.class);
	}

	@Override
	public UserResponseDto updateUser(UserDto user, Integer userId) {
		User userEntity = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(user.getPassword());

		userRepo.save(userEntity);

		return mapper.map(userEntity, UserResponseDto.class);
	}

	@Override
	public UserResponseDto getUserById(Integer userId) {
		User userEntity = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		return mapper.map(userEntity, UserResponseDto.class);
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		List<User> list = userRepo.findAll();

		List<UserResponseDto> userList = list.stream().map(user -> mapper.map(user, UserResponseDto.class))
				.collect(Collectors.toList());

		return userList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User userEntity = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		userRepo.delete(userEntity);
	}

	@Override
	public Set<UserResponseDto> follow(Integer userId, Integer followeeId) {
		User currentUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		User followee = userRepo.findById(followeeId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", followeeId));

		Set<User> followingList = currentUser.getFollowing();

		followingList.add(followee);
		followee.getFollowers().add(currentUser);

		userRepo.save(currentUser);
		userRepo.save(followee);

		Set<UserResponseDto> following = followingList.stream().map((user) -> mapper.map(user, UserResponseDto.class))
				.collect(Collectors.toSet());

		return following;
	}

	@Override
	public void unFollow(Integer userId, Integer followeeId) {
		User currentUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		User followee = userRepo.findById(followeeId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", followeeId));

		Set<User> followingList = currentUser.getFollowing();

		followingList.remove(followee);
		followee.getFollowers().remove(currentUser);

		userRepo.save(currentUser);
		userRepo.save(followee);
	}

	@Override
	public Set<UserResponseDto> block(Integer userId, Integer blockedId) {
		User currentUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		User blocked = userRepo.findById(blockedId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", blockedId));

		Set<User> blockedList = currentUser.getBlocked();

		blockedList.add(blocked);

		userRepo.save(currentUser);

		Set<UserResponseDto> list = blockedList.stream().map((user) -> mapper.map(user, UserResponseDto.class))
				.collect(Collectors.toSet());

		return list;
	}

	@Override
	public void unBlock(Integer userId, Integer blockedId) {
		User currentUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		User blocked = userRepo.findById(blockedId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", blockedId));

		Set<User> blockedList = currentUser.getBlocked();

		blockedList.remove(blocked);

		userRepo.save(currentUser);
	}

	@Override
	public Set<UserResponseDto> getFollowers(Integer userId) {
		User currentUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Set<User> followerList = currentUser.getFollowers();

		Set<UserResponseDto> followers = followerList.stream().map((user) -> mapper.map(user, UserResponseDto.class))
				.collect(Collectors.toSet());

		return followers;
	}

	@Override
	public Set<UserResponseDto> getFollowing(Integer userId) {
		User currentUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Set<User> followingsList = currentUser.getFollowing();

		Set<UserResponseDto> followings = followingsList.stream().map((user) -> mapper.map(user, UserResponseDto.class))
				.collect(Collectors.toSet());

		return followings;
	}

	@Override
	public Set<UserResponseDto> getBlocked(Integer userId) {
		User currentUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Set<User> blockedList = currentUser.getBlocked();

		Set<UserResponseDto> blocked = blockedList.stream().map((user) -> mapper.map(user, UserResponseDto.class))
				.collect(Collectors.toSet());

		return blocked;
	}

	@Override
	public List<UserResponseDto> searchUsers(String username, String email) {
		List<User> userList = userRepo.searchUser(username, email);
		
		List<UserResponseDto> list = userList.stream().map((user) -> mapper.map(user, UserResponseDto.class))
				.collect(Collectors.toList());
		
		return list;
	}
}
