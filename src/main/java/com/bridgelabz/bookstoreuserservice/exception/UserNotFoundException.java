package com.bridgelabz.bookstoreuserservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotFoundException extends RuntimeException {
	private int statuscode;
	private String message;

	public UserNotFoundException(int statuscode, String message) {
		super(message);
		this.statuscode = statuscode;
		this.message = message;
	}
}
