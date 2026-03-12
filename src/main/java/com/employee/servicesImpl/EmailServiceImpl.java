	package com.employee.servicesImpl;

	import org.springframework.mail.javamail.JavaMailSender;
	import org.springframework.mail.javamail.MimeMessageHelper;
	import org.springframework.stereotype.Service;
	import org.thymeleaf.context.Context;
	import org.thymeleaf.spring6.SpringTemplateEngine;
	import org.springframework.core.io.ClassPathResource;
	
	import com.employee.services.EmailService;
	
	import jakarta.mail.internet.MimeMessage;
	import lombok.RequiredArgsConstructor;
	
	@Service
	@RequiredArgsConstructor
	public class EmailServiceImpl implements EmailService {
		private final JavaMailSender mailSender;
		private final SpringTemplateEngine templateEngine;
	
		@Override
		public void sendLoginDetails(String personalEmail, String empId, String companyEmail, String password,
				String name) {
			
			
			try {
				  Context context = new Context();
		            context.setVariable("empId", empId);
		            context.setVariable("personalEmail", personalEmail);
		            context.setVariable("companyEmail", companyEmail);
		            context.setVariable("password", password);
		            context.setVariable("name", name);
		            
		            String htmlContent = templateEngine.process("EmailTemplate", context);
		            
		            MimeMessage message = mailSender.createMimeMessage();
		            MimeMessageHelper helper =
		                    new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
		            
		            helper.setTo(personalEmail);
		            helper.setSubject("Your Employee Login Details");
		            helper.setText(htmlContent, true);
		            
		            ClassPathResource logo =
		                    new ClassPathResource("email/Tektalis_Logo.png");

		            helper.addInline("tektalisLogo", logo, "image/png");
	
		            mailSender.send(message);
		            
			} catch (Exception e) {
	            throw new RuntimeException("Failed to send email", e);
	        }
	
		}
	
	}
