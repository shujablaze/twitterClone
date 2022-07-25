package com.shuja.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	private int id;
	private String username;
	private String email;
	private char[] password;
}
