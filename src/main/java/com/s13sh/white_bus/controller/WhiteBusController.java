package com.s13sh.white_bus.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("agency");
		session.removeAttribute("customer");
		session.setAttribute("successMessage", "Logout Success");
		return "redirect:/";
	}

	@GetMapping("/book-bus")
	public String loadBookBus() {
		return "book-bus.html";
	}

	@PostMapping("/book-bus")
	public String showBuses(@RequestParam String from, @RequestParam String to, @RequestParam LocalDate date,
			HttpSession session, ModelMap map) {
		return commonService.searchBus(from, to, date, session, map);
	}

}
