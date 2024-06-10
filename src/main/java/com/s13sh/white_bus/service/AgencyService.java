package com.s13sh.white_bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.s13sh.white_bus.dao.AgencyDao;
import com.s13sh.white_bus.dao.CustomerDao;
import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.helper.AES;
import com.s13sh.white_bus.helper.MailSendingHelper;

@Service
public class AgencyService {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	AgencyDao agencyDao;
	
	@Autowired
	MailSendingHelper mailSendingHelper;
  
	public String signup(Agency agency, BindingResult result) {
		if (!agency.getPassword().equals(agency.getCpassword()))
			result.rejectValue("cpassword", "error.cpassword", "* Password and Confirm Password Should be Matching");
		if (agencyDao.checkEmail(agency.getEmail()) || customerDao.checkEmail(agency.getEmail()))
			result.rejectValue("email", "error.email", "* Email Should be Unique");
		if (agencyDao.checkMobile(agency.getMobile()) || customerDao.checkMobile(agency.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number Should be Unique");

		if (result.hasErrors())
			return "agency-signup.html";
		else {
			agency.setPassword(AES.encrypt(agency.getPassword(), "123"));
			
			mailSendingHelper.sendEmail(agency);
			
			return "home.html";
		}
	}

}