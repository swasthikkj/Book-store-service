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

	@PutMapping("updateUser")
	public ResponseEntity<Response> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id, @RequestHeader String token) {
		UserModel userModel = userService.updateUser(userDTO, id, token);
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
	 * Purpose:trash user
	 */
	
	@GetMapping("/trashUser")
	public ResponseEntity<Response> trashUser(@RequestHeader String token) {
		UserModel userModel = userService.trashUser(token);
		Response response = new Response(200, "User moved to trash successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:restore user
	 */
	
	@GetMapping("/restoreUser")
	public ResponseEntity<Response> restoreUser(@RequestHeader String token) {
		UserModel userModel = userService.restoreUser(token);
		Response response = new Response(200, "User restored successfully", userModel);
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
	 * Purpose:reset user password
	 */

	@PostMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@RequestParam String password, @RequestHeader String token) {
		UserModel userModel = userService.resetPassword(password, token);
		Response response = new Response(200, "User password reset successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:change user password
	 * @Param password
	 */

	@PutMapping("/changePassword")
	public ResponseEntity<Response> changePassword(@PathVariable String token, @RequestParam String password, @RequestParam String newPassword) {
		UserModel userModel = userService.changePassword(token, password, newPassword);
		Response response = new Response(200, "User password reset successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:To set profile pic
	 * @throws IOException 
	 */

	@PutMapping("profilePic/{userId}")
	public ResponseEntity<Response> setProfilePic(@PathVariable Long userId, @RequestParam MultipartFile profilePic) throws IOException {
		UserModel userModel = userService.setProfilePic(userId, profilePic);
		Response response = new Response(200, "User profile pic set successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Purpose:To activate user
	 */

	@DeleteMapping("activateUser{userId}")
	public ResponseEntity<Response> activateUser(@PathVariable Long userId) {
		UserModel userModel = userService.activateUser(userId);
		Response response = new Response(200, "User activated successfully", userModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose:validate user
	 */

	@GetMapping("/validateUser/{token}")
	public Boolean validateUser(@PathVariable String token) {
		return userService.validateUser(token);
	}
}
