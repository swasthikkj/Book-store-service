package com.bridgelabz.bookstoreuserservice.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
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
	private long userId;
	private String firstName; 
	private String lastName; 
	private String dateOfBirth;
	private LocalDate registeredDate;
	private LocalDate updatedDate;
	private String password;
	private String emailId;
	private boolean verify;
	private int otp;
	@Column(length = 10000)
	private String profilePic;
	private Date purchaseDate;
	private Date expiryDate;
	
	public UserModel(UserDTO userDTO) {		
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.emailId = userDTO.getEmailId();
		this.password = userDTO.getPassword();
		this.dateOfBirth = userDTO.getDateOfBirth();
	}
}
