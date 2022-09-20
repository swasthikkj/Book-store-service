package com.bridgelabz.bookstoreuserservice.controller;

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
import com.bridgelabz.bookstoreuserservice.service.IUserService;
import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.util.Response;

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
	public ResponseEntity<Response> addUser(@Valid @RequestBody UserDTO userDTO) {
		UserModel userModel = userService.addUser(userDTO);
		Response response = new Response(200, "user added successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);		
	}

	/**
	 * Purpose:update user
	 */

	@PutMapping("updateUser/{userId}")
	public ResponseEntity<Response> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long userId, @RequestHeader String token) {
		UserModel userModel = userService.updateUser(userDTO, userId, token);
		Response response = new Response(200, "User updated successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get user by id
	 */

	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<Response> getUserById(@RequestHeader String token, @PathVariable Long userId) {
		UserModel userModel = userService.getUserById(token, userId);
		Response response = new Response(200, "USER fetched by id successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:get all users
	 */

	@GetMapping("/getAllUsers")
	public ResponseEntity<Response> getAllUsers(@RequestHeader String token) {
		List<UserModel> userModel = userService.getAllUsers(token);
		Response response = new Response(200, "All users fetched successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 * Purpose:delete user
	 */

	@DeleteMapping("deleteUser")
	public ResponseEntity<Response> deleteUser(@RequestHeader String token) {
		UserModel userModel = userService.deleteUser(token);
		Response response = new Response(200, "User deleted successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:login to generate token
	 * @Param enter email and password
	 */

	@PostMapping("/login")
	public Response login(@RequestParam String emailId, @RequestParam String password) {
		return userService.login(emailId, password);
	}

	/**
	 * Purpose:resetting forgot password
	 */

	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email) {
		UserModel userModel = userService.forgotPassword(email);
		Response response = new Response(200, "User password reset successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:reset user password
	 * @Param password
	 */

	@PutMapping("/resetPassword{token}")
	public ResponseEntity<Response> resetPassword(@PathVariable String token, @RequestParam String newPassword, @RequestParam String confirmPassword) {
		UserModel userModel = userService.resetPassword(token, confirmPassword, newPassword);
		Response response = new Response(200, "User password reset successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:generating OTP
	 */

	@PutMapping("/sendOTP")
	public ResponseEntity<Response> sendOTP(@RequestHeader String token) {
		UserModel userModel = userService.sendOTP(token);
		Response response = new Response(200, "User password reset successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:verifying OTP
	 */

	@GetMapping("/verifyOTP{otp}")
	public ResponseEntity<Response> verifyOTP(@RequestHeader String token, @PathVariable Integer otp) {
		boolean userModel = userService.verifyOTP(token, otp);
		Response response = new Response(200, "OTP verified", userModel);
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
