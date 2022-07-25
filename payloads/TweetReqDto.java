package com.shuja.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TweetReqDto {
	private int id;
	private String text;
	private int replyId;
}
