package com.bridgelabz.bookstoreuserservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.util.UserResponse;

public interface IUserService {
	
	UserModel addUser(UserDTO userDTO);
	
	UserModel updateUser(UserDTO userDTO, Long userId, String token);
		
	UserModel getUserById(String token, Long userId);

	List<UserModel> getAllUsers(String token);
	
	UserModel deleteUser(String token);
	
	UserResponse login(String emailId, String password);

	UserModel resetPassword(String token, String newPassword, String confirmPassword);

	UserModel forgotPassword(String emailId);

	UserModel sendOTP(String token);

	boolean verifyOTP(String token, Integer otp);

	UserResponse setProfilePic(Long userId, MultipartFile profile) throws IOException;

	Boolean verifyToken(String token);

	UserResponse validateUserId(Long userId);

	UserResponse purchaseSubscription(String token);

}
