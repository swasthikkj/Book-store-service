package com.bridgelabz.bookstoreuserservice.exception.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.bookstoreuserservice.exception.UserNotFoundException;
import com.bridgelabz.bookstoreuserservice.util.Response;
@ControllerAdvice
public class UserExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response> handleId(UserNotFoundException ab) {
		Response response = new Response();
		response.setErrorcode(400);
		response.setMessage(ab.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
