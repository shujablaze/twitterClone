package com.shuja.blog.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
	private int id;
	
	private String username;
	
	private String email;
	
	private Date joinedOn;
}
