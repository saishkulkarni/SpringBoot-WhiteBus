package com.s13sh.white_bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.service.AgencyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/agency")
public class AgencyController {

	@Autowired
	Agency agency;

	@Autowired
	AgencyService agencyService;

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("agency", agency);
		return "agency-signup.html";
	}

	@PostMapping("/signup")
	public String signup(@Valid Agency agency, BindingResult result) {
		return agencyService.signup(agency, result);
	}
}