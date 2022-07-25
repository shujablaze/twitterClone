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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shuja.blog.payloads.ApiResponse;
import com.shuja.blog.payloads.TweetReqDto;
import com.shuja.blog.payloads.UserResponseDto;
import com.shuja.blog.services.TweetService;

@RestController
@RequestMapping("/api/tweet")
public class TweetController {
	@Autowired
	private TweetService tweetService;
	
	@GetMapping("/{userId}")
	ResponseEntity<List<TweetReqDto>> getTweets(@PathVariable Integer userId){
		List<TweetReqDto> tweets = tweetService.getTweets(userId);
		
		return new ResponseEntity<>(tweets,HttpStatus.OK);
	}
	
	@PostMapping("/{userId}")
	ResponseEntity<TweetReqDto> postTweet(@PathVariable Integer userId,@RequestBody TweetReqDto tweet){
		if(tweet.getReplyId() == 0) tweet.setReplyId(-1);
		TweetReqDto createdTweet = tweetService.createTweet(userId, tweet.getText(), tweet.getReplyId());
		
		return new ResponseEntity<>(createdTweet,HttpStatus.CREATED); 
	}
	
	@DeleteMapping("/{userId}")
	void deleteTweet(@PathVariable Integer userId,@RequestBody Map<String,Integer> req) {
		tweetService.deleteTweet(userId, req.get("id"));
	}
	
	@GetMapping("/{userId}/timeline")
	ResponseEntity<List<TweetReqDto>> getTimeline(@PathVariable Integer userId){
		List<TweetReqDto> timeline = tweetService.getTimeline(userId);
		
		return new ResponseEntity<>(timeline,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/replies/{tweetId}")
	ResponseEntity<List<TweetReqDto>> getReplies(@PathVariable Integer userId,@PathVariable Integer tweetId){
		List<TweetReqDto> replies = tweetService.getReplies(userId,tweetId);
		
		return new ResponseEntity<>(replies,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/likes/{tweetId}")
	ResponseEntity<Set<UserResponseDto>> getLikes(@PathVariable Integer userId,@PathVariable Integer tweetId){
		Set<UserResponseDto> likes = tweetService.getLikes(userId, tweetId);
		return new ResponseEntity<>(likes,HttpStatus.OK); 
	}
	
	@PostMapping("/{userId}/likes")
	ResponseEntity<ApiResponse> likeTweets(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		tweetService.likeTweet(userId, req.get("id"));
		
		return new ResponseEntity<>(new ApiResponse(true,"Liked"),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{userId}/likes")
	void unlikeTweet(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		tweetService.unlikeTweet(userId, req.get("id"));
	}
	
	@GetMapping("/{userId}/retweet/{tweetId}")
	ResponseEntity<Set<UserResponseDto>> getRetweets(@PathVariable Integer userId,@PathVariable Integer tweetId){
		Set<UserResponseDto> retweets = tweetService.getRetweets(userId, tweetId);
		return new ResponseEntity<>(retweets,HttpStatus.OK); 
	}
	
	@PostMapping("/{userId}/retweet")
	ResponseEntity<ApiResponse> retweet(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		tweetService.reTweet(userId, req.get("id"));
		
		return new ResponseEntity<>(new ApiResponse(true,"Retweeted"),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{userId}/retweet")
	void unRetweet(@PathVariable Integer userId,@RequestBody Map<String,Integer> req){
		tweetService.unreTweet(userId, req.get("id"));
	}
	
}


