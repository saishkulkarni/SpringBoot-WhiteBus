package com.s13sh.white_bus.service;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.s13sh.white_bus.dto.Customer;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

public interface CustomerService {

	String signup(@Valid Customer customer, BindingResult result, HttpSession session);

	String verifyOtp(int id, int otp, HttpSession session);

	String resendOtp(int id, HttpSession session);

	String viewbookings(HttpSession session, ModelMap map);

}
