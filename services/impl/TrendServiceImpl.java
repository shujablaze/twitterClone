package com.shuja.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuja.blog.entities.Tag;
import com.shuja.blog.entities.Tweet;
import com.shuja.blog.exceptions.ResourceNotFoundException;
import com.shuja.blog.payloads.TagDto;
import com.shuja.blog.repositories.TagRepo;
import com.shuja.blog.services.TrendService;

@Service
public class TrendServiceImpl implements TrendService {
	
	@Autowired
	private TagRepo tagRepo;

	@Override
	public List<String> extractTags(String text) {
		StringBuilder str = new StringBuilder();
		
		
		for(int i = 0 ; i < text.length() ; ++i) {
			char c = text.charAt(i);
			if( c != '.' && c != ',') str.append(c);
		}

		String sanitizedText = str.toString();
		
		String[] words = sanitizedText.split(" ");
		
		List<String> tags = new ArrayList<String>();
		
		for(String s : words) 
			if(s.startsWith("#") && s.length() < 30) 
				tags.add(s);
		
		return tags;
	}

	@Override
	public void saveTags(List<String> tags,Tweet tweet) {
		
		tags.stream().forEach(t -> {
			Tag tag = new Tag();
			tag.setTagValue(t);
			tag.getTweets().add(tweet);
			tagRepo.save(tag);
		});

	}

	@Override
	public void deleteTag(Long tagId) {
		Tag tag = tagRepo.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

		tagRepo.delete(tag);
	}

	@Override
	public List<TagDto> getTopTrends() {
		// TODO Auto-generated method stub
		return null;
	}

}
