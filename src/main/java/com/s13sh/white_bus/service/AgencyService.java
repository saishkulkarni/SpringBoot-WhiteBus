package com.s13sh.white_bus.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.s13sh.white_bus.dao.AgencyDao;
import com.s13sh.white_bus.dao.CustomerDao;
import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.helper.AES;
import com.s13sh.white_bus.helper.MailSendingHelper;

import jakarta.servlet.http.HttpSession;

@Service
public class AgencyService {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	AgencyDao agencyDao;

	@Autowired
	MailSendingHelper mailSendingHelper;

	public String signup(Agency agency, BindingResult result, HttpSession session) {
		if (!agency.getPassword().equals(agency.getCpassword()))
			result.rejectValue("cpassword", "error.cpassword", "* Password and Confirm Password Should be Matching");
		if (agencyDao.checkEmail(agency.getEmail()) || customerDao.checkEmail(agency.getEmail()))
			result.rejectValue("email", "error.email", "* Email Should be Unique");
		if (agencyDao.checkMobile(agency.getMobile()) || customerDao.checkMobile(agency.getMobile()))
			result.rejectValue("mobile", "error.mobile", "* Mobile Number Should be Unique");

		if (result.hasErrors())
			return "agency-signup.html";
		else {
			agencyDao.deleteIfExists(agency);
			agency.setPassword(AES.encrypt(agency.getPassword(), "123"));
			agency.setOtp(new Random().nextInt(100000, 1000000));
			System.out.println("Otp - " + agency.getOtp());
			if (mailSendingHelper.sendEmail(agency)) {
				agencyDao.save(agency);
				session.setAttribute("successMessage", "Otp Sent Success");
				return "redirect:/agency/send-otp/" + agency.getId() + "";
			} else {
				return "redirect:/agency/signup";
			}

		}
	}

	public String verifyOtp(int id, int otp, HttpSession session) {
		Agency agency = agencyDao.findById(id);
		if (agency.getOtp() == otp) {
			agency.setStatus(true);
			agencyDao.save(agency);
			session.setAttribute("successMessage", "Otp Verified Success, You can Lgin Now");
			return "redirect:/agency/login";
		} else {
			session.setAttribute("failMessage", "Invalid Otp, Try Again");
			return "redirect:/agency/send-otp/" + agency.getId() + "";
		}
	}

	public String resendOtp(int id, HttpSession session) {
		Agency agency = agencyDao.findById(id);
		agency.setOtp(new Random().nextInt(100000, 1000000));
		System.out.println("Otp - " + agency.getOtp());
		if (mailSendingHelper.sendEmail(agency)) {
			agencyDao.save(agency);
			session.setAttribute("successMessage", "Otp Re-Sent Success");
			return "redirect:/agency/send-otp/" + agency.getId() + "";
		} else {
			session.setAttribute("failMessage", "Failed to Send Otp");
			return "redirect:/agency/send-otp/" + agency.getId() + "";
		}
	}

}