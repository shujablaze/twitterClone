package com.shuja.blog.exceptions;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class ResourceNotFoundException extends RuntimeException {
	
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	
	public ResourceNotFoundException(String resName,String fieldName,long val) {
		super(resName + " with " + fieldName + " " + val + " not found."); 
		this.resourceName = resName;
		this.fieldName = fieldName;
		this.fieldValue = val;
	}
}
