package com.bridgelabz.bookstoreuserservice.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.exception.UserNotFoundException;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.repository.UserRepository;
import com.bridgelabz.bookstoreuserservice.util.Response;
import com.bridgelabz.bookstoreuserservice.util.TokenUtil;;

@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	MailService mailService;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Purpose:add user and notify through email
	 */

	@Override
	public UserModel addUser(UserDTO userDTO) {
		UserModel model = new UserModel(userDTO);
		model.setCreatedAt(LocalDateTime.now());
		model.setDeleted(false);
		model.setActive(false);
		userRepository.save(model);
		String body = "User is added succesfully with userId " + model.getId();
		String subject = "User Registration Successfull";
		mailService.send(model.getEmailId(), subject, body);		
		return model;
	}

	/**
	 * Purpose:update user 
	 */

	@Override
	public UserModel updateUser(UserDTO userDTO, Long userId, String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<UserModel> isTokenPresent = userRepository.findById(decode);
		if (isTokenPresent.isPresent()) {
			Optional<UserModel> isUserPresent = userRepository.findById(userId);
			if(isUserPresent.isPresent()) {
				isUserPresent.get().setName(userDTO.getName());
				isUserPresent.get().setEmailId(userDTO.getEmailId());
				isUserPresent.get().setPassword(userDTO.getPassword());
				isUserPresent.get().setPhoneNumber(userDTO.getPhoneNumber());
				isUserPresent.get().setDateOfbirth(userDTO.getDateOfbirth());
				isUserPresent.get().setUpdatedAt(LocalDateTime.now());
				userRepository.save(isUserPresent.get());
				String body = "User updated successfully with user Id" + isUserPresent.get().getId();
				String subject = "User updated Successfully";
				mailService.send(isUserPresent.get().getEmailId(), subject, body);
				return isUserPresent.get();
			}
		}
		throw new UserNotFoundException(400, "User not present");
	}

	/**
	 * Purpose:fetch user by id
	 */

	@Override
	public UserModel getUserById(String token, Long userId) {	
		Long decode = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(decode);
		if (isUserPresent.isPresent()) {
			return isUserPresent.get();
		}
		throw new UserNotFoundException(400, "User not present");
	}

	/**
	 * Purpose:fetch all users
	 */

	@Override
	public List<UserModel> getAllUsers(String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(decode);
		if (isUserPresent.isPresent()) {
			List<UserModel> getAllUsers = userRepository.findAll();
			if(getAllUsers.size() > 0) {
				return getAllUsers;
			} else {
				throw new UserNotFoundException(400, "User not present");
			}
		}
		throw new UserNotFoundException(400, "Token not present");
	}

	/**
	 * Purpose:trash user
	 */

	@Override
	public UserModel trashUser(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setActive(false);
			isUserPresent.get().setDeleted(true);
			userRepository.save(isUserPresent.get());
			return isUserPresent.get();
		} 
		throw new UserNotFoundException(400,"User not present");	
	}

	/**
	 * Purpose:restore user
	 */

	@Override
	public UserModel restoreUser(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setActive(true);
			isUserPresent.get().setDeleted(false);
			userRepository.save(isUserPresent.get());
			return isUserPresent.get();
		} 
		throw new UserNotFoundException(400,"User not present");	
	}

	/**
	 * Purpose:delete user
	 */

	@Override
	public UserModel deleteUser(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setActive(false);
			isUserPresent.get().setDeleted(true);
			userRepository.delete(isUserPresent.get());
			return isUserPresent.get();
		}
		throw new UserNotFoundException(400,"User not present");
	}

	@Override
	public Response login(String emailId, String password) {
		Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
		if(isEmailPresent.isPresent()) {
			if(isEmailPresent.get().getPassword().equals(password) && isEmailPresent.get().isActive() == true) {
				String token = tokenUtil.createToken(isEmailPresent.get().getId());
				return new Response(400, "login succesfull", token);
			}
			throw new UserNotFoundException(400, "Invalid credentials");
		}
		throw new UserNotFoundException(400, "User not found");
	}

	/**
	 * Purpose:change user password
	 */

	@Override
	public UserModel changePassword(String token, String password, String newPassword) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(decode);
		if (isUserPresent.isPresent()) {
			if (isUserPresent.get().getPassword() == password) {
				isUserPresent.get().setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(isUserPresent.get());
				String body = "User password changed to new password successfully" + isUserPresent.get().getPassword();
				String subject = "User password changed Successfully";
				mailService.send(isUserPresent.get().getEmailId(), subject, body);
				return isUserPresent.get();
			}
		}
		throw new UserNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:reset user password
	 */

	@Override
	public UserModel resetPassword(String password, String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			isUserPresent.get().setPassword(passwordEncoder.encode(password));
			userRepository.save(isUserPresent.get());
			String body = "User password reset successfully" + isUserPresent.get().getPassword();
			String subject = "User password reset done Successfully";
			mailService.send(isUserPresent.get().getEmailId(), subject, body);
			return isUserPresent.get();
		}
		throw new UserNotFoundException(400, "Email not found");
	}

	/**
	 * Purpose:reset user password
	 */

	@Override
	public UserModel activateUser(Long userId) {
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			isUserPresent.get().setActive(true);
			userRepository.save(isUserPresent.get());
			String body = "User activated successfully" + isUserPresent.get().getId();
			String subject = "User is in active mode";
			mailService.send(isUserPresent.get().getEmailId(), subject, body);
			return isUserPresent.get();
		}
		throw new UserNotFoundException(400, "User not found");
	}

	/**
	 * Purpose:setting profile pic of user
	 */

	@Override
	public UserModel setProfilePic(Long userId, MultipartFile profile) throws IOException {
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setProfilePic(String.valueOf(profile.getBytes()));
			userRepository.save(isUserPresent.get());
			return isUserPresent.get();
		}
		throw new UserNotFoundException(400, "User not found");
	}

	/**
	 * Purpose:validate user
	 */

	@Override
	public Boolean validateUser(String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<UserModel> isTokenPresent = userRepository.findById(decode);
		if (isTokenPresent.isPresent())
			return true;
		throw new UserNotFoundException(400, "Token not found");
	}
}
