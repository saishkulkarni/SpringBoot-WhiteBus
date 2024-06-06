package com.s13sh.white_bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.s13sh.white_bus.dto.Agency;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/agency")
public class AgencyController {

	@PostMapping("/signup")
	public String signup(@Valid Agency agency, BindingResult result) {
		if (result.hasErrors())
			return "agency-signup.html";
		else
			return "home.html";
	}
}
