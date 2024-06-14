package com.s13sh.white_bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s13sh.white_bus.dao.AgencyDao;
import com.s13sh.white_bus.dao.CustomerDao;
import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.dto.Customer;
import com.s13sh.white_bus.helper.AES;

import jakarta.servlet.http.HttpSession;

@Service
public class CommonService {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	AgencyDao agencyDao;

	public String signup(String role) {
		if (role.equals("customer"))
			return "redirect:/customer/signup";
		else
			return "redirect:/agency/signup";
	}

	public String login(String emph, String password, HttpSession session) {
		Agency agency = null;
		Customer customer = null;
		try {
			long mobile = Long.parseLong(emph);
			customer = customerDao.findByMobile(mobile);
			agency = agencyDao.findByMobile(mobile);
		} catch (NumberFormatException e) {
			String email = emph;
			customer = customerDao.findByEmail(email);
			agency = agencyDao.findByEmail(email);
		}
		if (customer == null && agency == null) {
			session.setAttribute("failMessage", "Invalid Email or Phone");
			return "redirect:/login";
		} else {
			if (customer == null) {
				if (AES.decrypt(agency.getPassword(), "123").equals(password)) {
					session.setAttribute("agency", agency);
					session.setAttribute("successMessage", "Login Success");
					return "redirect:/";
				} else {
					session.setAttribute("failMessage", "Invalid Password");
					return "redirect:/login";
				}
			} else {
				if (AES.decrypt(customer.getPassword(), "123").equals(password)) {
					session.setAttribute("customer", customer);
					session.setAttribute("successMessage", "Login Success");
					return "redirect:/";
				} else {
					session.setAttribute("failMessage", "Invalid Password");
					return "redirect:/login";
				}
			}
		}

	}

}
