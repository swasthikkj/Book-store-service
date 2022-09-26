package com.bridgelabz.bookstoreuserservice.config;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.repository.UserRepository;
import com.bridgelabz.bookstoreuserservice.service.MailService;

@Component
public class ShedulingConfig {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MailService mailService;

	@Scheduled(fixedDelay = 60000)
	public void emailShedulingJob() {
		List<UserModel> usersList = userRepository.findAll();
		for(UserModel userModel : usersList) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(userModel.getPurchaseDate());
			calendar.add(Calendar.MONTH, 11);
			Date date = calendar.getTime();
			if(new Date().equals(date)) {
				String body = "your subscription expires in one month :" + userModel.getUserId();
				String subject = "Please subscribe";
				mailService.send(userModel.getEmailId(), subject, body);
			}
			Calendar calendar2 = Calendar.getInstance();
			calendar.setTime(userModel.getPurchaseDate());
			calendar.add(Calendar.MONTH, 12);
			Date date2 = calendar2.getTime();
			if(userModel.getExpiryDate() == date2) {
				String body = "your subscription expired :" + userModel.getUserId();
				String subject = "Please subscribe";
				mailService.send(userModel.getEmailId(), subject, body);
			}	
		}
	}
}