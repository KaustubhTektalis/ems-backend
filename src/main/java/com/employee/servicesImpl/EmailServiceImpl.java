package com.employee.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import com.employee.services.EmailService;



public class EmailServiceImpl implements EmailService{
	private JavaMailSender mailSender;
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	@Override
	public void sendLoginDetails(String personalEmail,String empId,String companyEmail,String password) {
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(personalEmail);
		mail.setSubject("Welcome Onboard");
		mail.setText("Your Account has created in the EMS \n\n"
		+"Employee ID:"+empId +"\n"
		+"Company Email:"+companyEmail+"\n"
		+"Password:"+password
		+"\n\n"
		+"Please Login and Change Your password");
		
		mailSender.send(mail);
		
	}

}
