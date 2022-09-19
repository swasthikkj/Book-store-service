package com.bridgelabz.bookstoreuserservice.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.util.Response;

public interface IUserService {
	
	UserModel addUser(UserDTO userDTO);
	
	UserModel updateUser(UserDTO userDTO, Long userId, String token);
		
	UserModel getUserById(String token, Long userId);

	List<UserModel> getAllUsers(String token);
	
	UserModel trashUser(String token);
	
	UserModel restoreUser(String token);
	
	UserModel deleteUser(String token);
	
	Response login(String emailId, String password);

	UserModel changePassword(String token, String password, String newPassword);

	UserModel resetPassword(String password, String token);

	Boolean validateUser(String token);

	UserModel setProfilePic(Long userId, MultipartFile profile) throws IOException;

	UserModel activateUser(Long userId);

}
