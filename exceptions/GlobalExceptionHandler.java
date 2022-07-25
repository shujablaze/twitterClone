package com.shuja.blog.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shuja.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
		String message = e.getMessage();
		
		ApiResponse res = new ApiResponse(false,message);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	ResponseEntity<ApiResponse> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e){
		String message = e.getMessage();
		
		ApiResponse res = new ApiResponse(false,message);
		
		return new ResponseEntity<ApiResponse>(res,HttpStatus.NOT_FOUND);
	}
}
