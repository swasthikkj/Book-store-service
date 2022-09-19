package com.bridgelabz.bookstoreuserservice.model;

import java.time.LocalDateTime;

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
@Table(name = "BookStore")
@Data
@NoArgsConstructor
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String emailId;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean isActive;
	private boolean isDeleted;
	private String dateOfbirth;
	private long phoneNumber;
	private String profilePic;
	
	public UserModel(UserDTO userServiceDTO) {		
		this.name = userServiceDTO.getName();
		this.emailId = userServiceDTO.getEmailId();
		this.password = userServiceDTO.getPassword();
		this.dateOfbirth = userServiceDTO.getDateOfbirth();
		this.phoneNumber = userServiceDTO.getPhoneNumber();
	}
}
