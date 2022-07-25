package com.shuja.blog.services;

import java.util.List;
import java.util.Set;

import com.shuja.blog.payloads.TweetReqDto;
import com.shuja.blog.payloads.UserResponseDto;

public interface TweetService {
	TweetReqDto createTweet(Integer userId,String text,Integer replyId);
	
	void deleteTweet(Integer userId,Integer tweetId);
	
	void likeTweet(Integer userId,Integer tweetId);
	
	void unlikeTweet(Integer userId,Integer tweetId);
	
	void reTweet(Integer userId,Integer tweetId);
	
	void unreTweet(Integer userId,Integer tweetId);
	
	List<TweetReqDto> getTweets(Integer userId);
	
	List<TweetReqDto> getReplies(Integer userId,Integer tweetId);
	
	List<TweetReqDto> getTimeline(Integer userId);
	
	Set<UserResponseDto> getRetweets(Integer userId,Integer tweetId);

	Set<UserResponseDto> getLikes(Integer userId, Integer tweetId);
}
