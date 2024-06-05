package com.s13sh.white_bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
