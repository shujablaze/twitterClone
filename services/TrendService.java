package com.shuja.blog.services;

import java.util.List;

import com.shuja.blog.entities.Tweet;
import com.shuja.blog.payloads.TagDto;

public interface TrendService {
	
	List<String> extractTags(String text);
	
	void saveTags(List<String> tags,Tweet tweet);
	
	void deleteTag(Long tagId);
	
	List<TagDto> getTopTrends();
}
