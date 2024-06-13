package com.s13sh.white_bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.s13sh.white_bus.service.CommonService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WhiteBusController {

	@Autowired
	CommonService commonService;

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
		return commonService.signup(role);
	}

	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	@PostMapping("/login")
	public String login(@RequestParam("email-phone") String emph, String password, HttpSession session) {
		return commonService.login(emph, password, session);
	}

}
