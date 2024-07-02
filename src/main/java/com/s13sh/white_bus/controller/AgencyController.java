package com.s13sh.white_bus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.dto.Bus;
import com.s13sh.white_bus.dto.Route;
import com.s13sh.white_bus.service.AgencyService;

import jakarta.servlet.http.HttpSession;
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
	public String signup(@Valid Agency agency, BindingResult result, HttpSession session) {
		return agencyService.signup(agency, result, session);
	}

	@GetMapping("/send-otp/{id}")
	public String loadOtpPage(@PathVariable int id, ModelMap map) {
		map.put("id", id);
		return "agency-otp.html";
	}

	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam int id, @RequestParam int otp, HttpSession session) {
		return agencyService.verifyOtp(id, otp, session);
	}
	
	@GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable int id, HttpSession session) {
		return agencyService.resendOtp(id, session);
	}

	@GetMapping("/add-bus")
	public String addBus() {
		return "add-bus.html";
	}

	@PostMapping("/add-bus")
	public String addBus(Bus bus, @RequestParam MultipartFile image, HttpSession session) {
		return agencyService.addBus(bus, image, session);
	}

	@GetMapping("/add-route")
	public String addRoute(HttpSession session, ModelMap map) {
		return agencyService.addRoute(session, map);
	}

	@PostMapping("/add-route")
	public String addRoute(Route route, HttpSession session) {
		return agencyService.addRoute(route, session);
	}

	@GetMapping("/manage-route")
	public String manageRoute(HttpSession session, ModelMap map) {
		return agencyService.fetchRoutes(session, map);
	}

	@GetMapping("/delete-route/{id}")
	public String deleteRoute(@PathVariable int id, HttpSession session) {
		return agencyService.deleteRoute(id, session);
	}
	
	@GetMapping("/edit-route/{id}")
	public String editRoute(@PathVariable int id, HttpSession session,ModelMap map) {
		return agencyService.editRoute(id, session,map);
	}
	
	@GetMapping("/manage-bus")
	public String manageBus(HttpSession session, ModelMap map) {
		return agencyService.fetchBuses(session, map);
	}
	
	@GetMapping("/delete-bus/{id}")
	public String deleteBus(@PathVariable int id, HttpSession session) {
		return agencyService.deleteBus(id, session);
	}
	
	@GetMapping("/edit-bus/{id}")
	public String editBus(@PathVariable int id, HttpSession session,ModelMap map) {
		return agencyService.editBus(id, session,map);
	}
	
	@PostMapping("/edit-bus")
	public String editBus(Bus bus, @RequestParam MultipartFile image,HttpSession session) {
		return agencyService.editBus(bus,image,session);
	}
	
	
}
