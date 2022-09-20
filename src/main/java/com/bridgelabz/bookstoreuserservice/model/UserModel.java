package com.bridgelabz.bookstoreuserservice.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose:Model for user service
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@Entity
@Table(name = "BookStoreUser")
@Data
@NoArgsConstructor
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String firstName; 
	private String lastName; 
	private String dateOfBirth;
	private LocalDate registeredDate;
	private LocalDate updatedDate;
	private String password;
	private String emailId;
	private boolean verify;
	private int otp;
	private String profilePic;
	private LocalDate purchaseDate;
	private LocalDate expiryDate;
	
	public UserModel(UserDTO userDTO) {		
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.emailId = userDTO.getEmailId();
		this.password = userDTO.getPassword();
		this.dateOfBirth = userDTO.getDateOfBirth();
	}
}
