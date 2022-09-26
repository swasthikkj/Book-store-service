package com.bridgelabz.bookstoreuserservice.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import com.bridgelabz.bookstoreuserservice.util.UserResponse;
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
		model.setRegisteredDate(LocalDate.now());
		userRepository.save(model);
		String body = "User is added succesfully with userId " + model.getUserId();
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
				isUserPresent.get().setFirstName(userDTO.getFirstName());
				isUserPresent.get().setLastName(userDTO.getLastName());
				isUserPresent.get().setEmailId(userDTO.getEmailId());
				isUserPresent.get().setPassword(userDTO.getPassword());
				isUserPresent.get().setDateOfBirth(userDTO.getDateOfBirth());
				isUserPresent.get().setUpdatedDate(LocalDate.now());
				userRepository.save(isUserPresent.get());
				String body = "User updated successfully with user Id" + isUserPresent.get().getUserId();
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
	 * Purpose:delete user
	 */

	@Override
	public UserModel deleteUser(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			userRepository.delete(isUserPresent.get());
			return isUserPresent.get();
		}
		throw new UserNotFoundException(400,"User not present");
	}

	/**
	 * Purpose:setting profile pic of user
	 */

	@Override
	public UserResponse setProfilePic(Long userId, MultipartFile profile) throws IOException {
		Optional<UserModel> isIdPresent = userRepository.findById(userId);
		if(isIdPresent.isPresent()) {
			isIdPresent.get().setProfilePic(String.valueOf(profile.getBytes()));
			userRepository.save(isIdPresent.get());
			return new UserResponse(400, "Success", isIdPresent.get());
		}
		throw new UserNotFoundException(400, "User not found");
	}

	/**
	 * Purpose:login user to generate token
	 */

	@Override
	public UserResponse login(String emailId, String password) {
		Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
		if(isEmailPresent.isPresent()) {
			if(isEmailPresent.get().getPassword().equals(password)) {
				String token = tokenUtil.createToken(isEmailPresent.get().getUserId());
				return new UserResponse(400, "login succesfull", token);
			}
			throw new UserNotFoundException(400, "Invalid credentials");
		}
		throw new UserNotFoundException(400, "User not found");
	}

	/**
	 * Purpose:reset user password
	 */

	@Override
	public UserModel resetPassword(String token, String newPassword, String confirmPassword) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			if(newPassword.equals(confirmPassword)) {
				isUserPresent.get().setPassword(newPassword);
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
	 * Purpose:resetting forgot password3
	 */

	@Override
	public UserModel forgotPassword(String emailId) {
		Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
		if (isEmailPresent.isPresent()) {
			String token = tokenUtil.createToken(isEmailPresent.get().getUserId());
			String url = "Click the link to reset password \n" + "http://localhost:8090/userService/resetPassword" + token;
			String subject = "Link to reset password";
			mailService.send(isEmailPresent.get().getEmailId(), subject, url);
			return isEmailPresent.get();
		}
		throw new UserNotFoundException(400, "Token not present");
	}

	/**
	 * Purpose:Generating OTP 
	 */

	@Override
	public UserModel sendOTP(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			int min = 100000, max = 999999;
			int random = (int)(Math.random()*(max-min+1)+min);
			isUserPresent.get().setOtp(random);
			userRepository.save(isUserPresent.get());
			String body = isUserPresent.get().getOtp() + "use this otp";
			String subject = "OTP generated successfully";
			mailService.send(isUserPresent.get().getEmailId(), subject, body);
			return isUserPresent.get();
		}
		throw new UserNotFoundException(400,"OTP is invalid");
	}

	/**
	 * Purpose:Verifying OTP 
	 */

	@Override
	public boolean verifyOTP(String token, Integer otp) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			if(isUserPresent.get().getOtp() == otp) {
				isUserPresent.get().setVerify(true);
				userRepository.save(isUserPresent.get());
				return isUserPresent.get().isVerify();
			}
		}
		throw new UserNotFoundException(400, "OTP is invalid");
	}

	/**
	 * Purpose:verify token
	 */

	@Override
	public Boolean verifyToken(String token) {
		Long decode = tokenUtil.decodeToken(token);
		Optional<UserModel> isTokenPresent = userRepository.findById(decode);
		if (isTokenPresent.isPresent())
			return true;
		throw new UserNotFoundException(400, "Token not found");
	}

	/**
	 * Purpose:validate user id
	 */

	@Override
	public UserResponse validateUserId(Long userId) {
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if (isUserPresent.isPresent()) {
			return new UserResponse(200, "User Validate Successfully", isUserPresent.get());
		}
		throw new UserNotFoundException(400, "User Not Found");
	}

	@Override
	public UserResponse purchaseSubscription(String token) {
		Long userId = tokenUtil.decodeToken(token);
		Optional<UserModel> isUserPresent = userRepository.findById(userId);
		if(isUserPresent.isPresent()) {
			isUserPresent.get().setPurchaseDate(new Date());
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 12);
			Date expireDate = calendar.getTime();
			isUserPresent.get().setExpiryDate(expireDate);
			userRepository.save(isUserPresent.get());
			return new UserResponse(200, "User Validate Successfully", isUserPresent.get());
		}
		throw new UserNotFoundException(400, "User Not Found");
	}
}
