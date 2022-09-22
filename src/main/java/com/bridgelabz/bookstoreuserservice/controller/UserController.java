package com.bridgelabz.bookstoreuserservice.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreuserservice.service.IUserService;
import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.util.UserResponse;

/**
 * Purpose:create user service controller
 * @version 4.15.1.RELEASE
 * @author Swasthik KJ
 */

@RestController
@RequestMapping("/userService")
public class UserController {
	@Autowired
	IUserService userService;
	
	/**
	 * Purpose:add user
	 */

	@PostMapping("/addUser")
	public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserDTO userDTO) {
		UserModel userModel = userService.addUser(userDTO);
		UserResponse response = new UserResponse(200, "user added successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);		
	}

	/**
	 * Purpose:update user
	 */

	@PutMapping("updateUser/{userId}")
	public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long userId, @RequestHeader String token) {
		UserModel userModel = userService.updateUser(userDTO, userId, token);
		UserResponse response = new UserResponse(200, "User updated successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get user by id
	 */

	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<UserResponse> getUserById(@RequestHeader String token, @PathVariable Long userId) {
		UserModel userModel = userService.getUserById(token, userId);
		UserResponse response = new UserResponse(200, "USER fetched by id successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get all users
	 */

	@GetMapping("/getAllUsers")
	public ResponseEntity<UserResponse> getAllUsers(@RequestHeader String token) {
		List<UserModel> userModel = userService.getAllUsers(token);
		UserResponse response = new UserResponse(200, "All users fetched successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose:delete user
	 */

	@DeleteMapping("deleteUser")
	public ResponseEntity<UserResponse> deleteUser(@RequestHeader String token) {
		UserModel userModel = userService.deleteUser(token);
		UserResponse response = new UserResponse(200, "User deleted successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:To set profile pic
	 * @throws IOException 
	 */

	@PutMapping("profilePic/{id}")
	public ResponseEntity<UserResponse> setProfilePic(@PathVariable Long id, @RequestParam MultipartFile profilePic) throws IOException {
		UserResponse userModel = userService.setProfilePic(id, profilePic);
		UserResponse response = new UserResponse(200, "User profile pic set successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:login to generate token
	 * @Param enter email and password
	 */

	@PostMapping("/login")
	public UserResponse login(@RequestParam String emailId, @RequestParam String password) {
		return userService.login(emailId, password);
	}

	/**
	 * Purpose:resetting forgot password
	 */

	@PostMapping("/forgotPassword")
	public ResponseEntity<UserResponse> forgotPassword(@RequestParam String email) {
		UserModel userModel = userService.forgotPassword(email);
		UserResponse response = new UserResponse(200, "User password reset successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:reset user password
	 * @Param password
	 */

	@PutMapping("/resetPassword{token}")
	public ResponseEntity<UserResponse> resetPassword(@PathVariable String token, @RequestParam String newPassword, @RequestParam String confirmPassword) {
		UserModel userModel = userService.resetPassword(token, confirmPassword, newPassword);
		UserResponse response = new UserResponse(200, "User password reset successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:generating OTP
	 */

	@PutMapping("/sendOTP")
	public ResponseEntity<UserResponse> sendOTP(@RequestHeader String token) {
		UserModel userModel = userService.sendOTP(token);
		UserResponse response = new UserResponse(200, "OTP sent successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:verifying OTP
	 */

	@GetMapping("/verifyOTP{otp}")
	public ResponseEntity<UserResponse> verifyOTP(@RequestHeader String token, @PathVariable Integer otp) {
		boolean userModel = userService.verifyOTP(token, otp);
		UserResponse response = new UserResponse(200, "OTP verified", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:verifying token
	 */

	@GetMapping("/verifyToken/{token}")
	public Boolean verifyToken(@PathVariable String token) {
		return userService.verifyToken(token);
		
	}
}
