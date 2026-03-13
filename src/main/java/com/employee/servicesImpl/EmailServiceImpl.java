package com.employee.servicesImpl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.employee.services.EmailService;

import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService {
	private JavaMailSender mailSender;

	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendLoginDetails(String personalEmail, String empId, String companyEmail, String password) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(personalEmail);
		mail.setSubject("Welcome Onboard");
		mail.setText("Your Account has created in the EMS \n\n" + "Employee ID:" + empId + "\n" + "Company Email:"
				+ companyEmail + "\n" + "Password:" + password + "\n\n" + "Please Login and Change Your password");

		mailSender.send(mail);

	}
	
	@Override
	public void sendResetPasswordEmail(String email,String link) {

	    MimeMessage message = mailSender.createMimeMessage();

	    try {

	        MimeMessageHelper helper =
	                new MimeMessageHelper(message,true);

	        helper.setTo(email);

	        helper.setSubject("Reset Password");

	        String html = """
	                <h2>Password Reset</h2>
	                <p>Click below link to reset password</p>
	                <a href="%s">Reset Password</a>
	                """.formatted(link);

	        helper.setText(html,true);

	        mailSender.send(message);

	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	

}
