package com.shuja.blog.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuja.blog.entities.Tweet;
import com.shuja.blog.entities.User;
import com.shuja.blog.exceptions.ResourceNotFoundException;
import com.shuja.blog.payloads.TweetReqDto;
import com.shuja.blog.payloads.UserResponseDto;
import com.shuja.blog.repositories.TweetRepo;
import com.shuja.blog.repositories.UserRepo;
import com.shuja.blog.services.TrendService;
import com.shuja.blog.services.TweetService;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TweetRepo tweetRepo;
	
	@Autowired
	private TrendService trendService;

	@Autowired
	ModelMapper mapper;

	@Override
	public TweetReqDto createTweet(Integer userId, String text, Integer replyId) {
		Tweet tweet = new Tweet();

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		tweet.setAuthor(user);
		tweet.setText(text);

		if (replyId != -1) {
			Tweet replyTweet = tweetRepo.findById(replyId)
					.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", replyId));
			tweet.setReplyTo(replyTweet);
		}
		
		List<String> extractedTags = trendService.extractTags(tweet.getText());
		
		tweetRepo.save(tweet);
		trendService.saveTags(extractedTags, tweet);

		return mapper.map(tweet, TweetReqDto.class);
	}

	@Override
	public void deleteTweet(Integer userId, Integer tweetId) {
		userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		tweetRepo.delete(tweet);
	}

	@Override
	public void likeTweet(Integer userId, Integer tweetId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		tweet.getLikes().add(user);

		tweetRepo.save(tweet);
	}

	@Override
	public void unlikeTweet(Integer userId, Integer tweetId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		tweet.getLikes().remove(user);

		tweetRepo.save(tweet);

	}

	@Override
	public void reTweet(Integer userId, Integer tweetId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		tweet.getRetweets().add(user);

		tweetRepo.save(tweet);

	}

	@Override
	public void unreTweet(Integer userId, Integer tweetId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		tweet.getRetweets().remove(user);

		tweetRepo.save(tweet);

	}

	@Override
	public List<TweetReqDto> getTweets(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		List<TweetReqDto> tweets = user.getTweets().stream().map((t) -> mapper.map(t, TweetReqDto.class))
				.collect(Collectors.toList());

		return tweets;
	}

	@Override
	public Set<UserResponseDto> getLikes(Integer userId, Integer tweetId) {
		userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		Set<UserResponseDto> likes = tweet.getLikes().stream().map((t) -> mapper.map(t, UserResponseDto.class))
				.collect(Collectors.toSet());

		return likes;
	}

	@Override
	public List<TweetReqDto> getReplies(Integer userId, Integer tweetId) {
		userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		List<TweetReqDto> replies = tweet.getReply().stream().map((t) -> mapper.map(t, TweetReqDto.class))
				.collect(Collectors.toList());

		tweet.getReply().stream().forEach(e -> System.out.println(e.getText()));

		return replies;
	}

	@Override
	public Set<UserResponseDto> getRetweets(Integer userId, Integer tweetId) {
		userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		Tweet tweet = tweetRepo.findById(tweetId)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet", "id", tweetId));

		Set<UserResponseDto> retweets = tweet.getRetweets().stream().map((t) -> mapper.map(t, UserResponseDto.class))
				.collect(Collectors.toSet());

		return retweets;
	}

	@Override
	public List<TweetReqDto> getTimeline(Integer userId) {
		List<Tweet> timeline = tweetRepo.getTimeline(userId);

		List<TweetReqDto> tl = timeline.stream().map(t -> mapper.map(t, TweetReqDto.class)).collect(Collectors.toList());

		return tl;
	}

}
