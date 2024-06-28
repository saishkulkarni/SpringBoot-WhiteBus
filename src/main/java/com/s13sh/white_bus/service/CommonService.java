package com.s13sh.white_bus.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.s13sh.white_bus.dao.AgencyDao;
import com.s13sh.white_bus.dao.CustomerDao;
import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.dto.Customer;
import com.s13sh.white_bus.dto.Route;
import com.s13sh.white_bus.dto.Station;
import com.s13sh.white_bus.helper.AES;
import com.s13sh.white_bus.repository.RouteRepository;
import com.s13sh.white_bus.repository.StationRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CommonService {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	AgencyDao agencyDao;

	@Autowired
	StationRepository stationRepository;

	@Autowired
	RouteRepository routeRepository;

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

	public String searchBus(String from, String to, LocalDate date, HttpSession session, ModelMap map) {
		if (!from.equalsIgnoreCase(to)) {
			if (date.isAfter(LocalDate.now()) || date.isEqual(LocalDate.now())) {
				String day = date.getDayOfWeek().toString().toLowerCase();
				List<Route> routes = new ArrayList<Route>();

				List<Station> fromStations = stationRepository.findByName(from);
				List<Station> toStations = stationRepository.findByName(to);

				for (Station from1 : fromStations) {
					for (Station to1 : toStations) {
						if (from1.getRoute().getId() == to1.getRoute().getId()) {
							Route route = from1.getRoute();
							if (route.getStations().indexOf(from1) < route.getStations().indexOf(to1)
									|| route.getStations().indexOf(to1) == 1)
								switch (day) {
								case "monday": {
									if (route.isMonday())
										routes.add(route);
									break;
								}
								case "tuesday": {
									if (route.isTuesday())
										routes.add(route);
									break;
								}
								case "wednesday": {
									if (route.isWednesday())
										routes.add(route);
									break;
								}
								case "thursday": {
									if (route.isThursday())
										routes.add(route);
									break;
								}
								case "friday": {
									if (route.isFriday())
										routes.add(route);
								}
								case "saturday": {
									if (route.isSaturday())
										routes.add(route);
									break;
								}
								case "sunday": {
									if (route.isSunday())
										routes.add(route);
									break;
								}

								default:
									throw new IllegalArgumentException("Unexpected value: " + day);
								}
						}
					}
				}

				if (routes.isEmpty()) {
					session.setAttribute("failMessage", "No Bus Found");
					return "redirect:/book-bus";
				} else {
					map.put("from", from);
					map.put("to", to);
					map.put("routes", routes);
					return "view-routes.html";
				}
			} else {
				session.setAttribute("failMessage", "Select Proper Date");
				return "redirect:/book-bus";
			}

		} else {
			session.setAttribute("failMessage", "Enter Proper Destination");
			return "redirect:/book-bus";
		}
	}

}
