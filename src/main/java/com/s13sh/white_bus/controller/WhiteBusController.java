package com.s13sh.white_bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WhiteBusController {

	@GetMapping("/")
	public String loadHome() {
		return "home.html";
	}

	@GetMapping("/signup")
	public String loadSignup() {
		return "signup.html";
	}

	@PostMapping("/signup")
	public String signup(@RequestParam String role) {
		if (role.equals("customer"))
			return "customer-signup";
		else
			return "agency-signup";
	}

}
