package com.bridgelabz.bookstoreuserservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
	private int errorcode;
	private String message;
	private Object token;
	
	public Response() {
		
	}
}
