package com.s13sh.white_bus.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.s13sh.white_bus.dto.Agency;

@Service
public class MailSendingHelper {
	
	@Autowired
	JavaMailSender mailSender;
	
	public boolean sendEmail(Agency agency) {
		return true;
	}

}
