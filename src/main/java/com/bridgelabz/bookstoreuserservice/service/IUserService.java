package com.bridgelabz.bookstoreuserservice.service;

import java.util.List;
import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.util.Response;

public interface IUserService {
	
	UserModel addUser(UserDTO userDTO);
	
	UserModel updateUser(UserDTO userDTO, Long userId, String token);
		
	UserModel getUserById(String token, Long userId);

	List<UserModel> getAllUsers(String token);
	
	UserModel deleteUser(String token);
	
	Response login(String emailId, String password);

	UserModel resetPassword(String token, String newPassword, String confirmPassword);

	UserModel forgotPassword(String emailId);

	Boolean verifyToken(String token);

	UserModel sendOTP(String token);

	boolean verifyOTP(String token, Integer otp);

}
