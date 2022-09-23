package com.bridgelabz.bookstoreuserservice.dto;

import javax.validation.constraints.Pattern;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;

import lombok.Data;

/**
 * Purpose:DTO for user service
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Data
public class UserDTO {
	@Pattern(regexp = "^[A-Z]{1}[a-z\\s]{2,}$", message = "First name is Invalid")
	private String firstName;
	@Pattern(regexp = "^[a-zA-Z\\s]{2,}$", message = "Last name Invalid")
	private String lastName; 
	private String dateOfBirth;
	@Pattern(regexp = "^[a-zA-Z0-9*&@]{2,20}$", message = "password is invalid")
	private String password;
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "email is invalid")
	private String emailId;
}
