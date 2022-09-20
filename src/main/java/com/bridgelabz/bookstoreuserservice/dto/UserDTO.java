package com.bridgelabz.bookstoreuserservice.dto;

import java.time.LocalDate;

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
	private String firstName; 
	private String lastName; 
	private String dateOfBirth;
	private String password;
	private String emailId;
}
