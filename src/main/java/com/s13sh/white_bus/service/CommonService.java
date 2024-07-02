package com.s13sh.white_bus.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.s13sh.white_bus.dao.AgencyDao;
import com.s13sh.white_bus.dao.CustomerDao;
import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.dto.Bus;
import com.s13sh.white_bus.dto.Customer;
import com.s13sh.white_bus.dto.Route;
import com.s13sh.white_bus.dto.Station;
import com.s13sh.white_bus.dto.TripOrder;
import com.s13sh.white_bus.helper.AES;
import com.s13sh.white_bus.helper.Calculator;
import com.s13sh.white_bus.repository.BusRepository;
import com.s13sh.white_bus.repository.CustomerRepository;
import com.s13sh.white_bus.repository.RouteRepository;
import com.s13sh.white_bus.repository.StationRepository;
import com.s13sh.white_bus.repository.TripOrderRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CommonService {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	AgencyDao agencyDao;

	@Autowired
	Calculator calculator;

	@Autowired
	BusRepository busRepository;

	@Autowired
	TripOrderRepository orderRepository;

	@Autowired
	StationRepository stationRepository;

	@Autowired
	RouteRepository routeRepository;
	
	@Autowired
	CustomerRepository customerRepository;

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

	public String bookTicket(String from, String to, int routeId, HttpSession session, ModelMap map, int seat) {
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			session.setAttribute("failMessage", "First Login to Book");
			return "redirect:/login";
		} else {
			Route route = routeRepository.findById(routeId).orElseThrow();
			Bus bus = route.getBus();
			if (bus.getSeat() >= seat) {
				double price = calculator.calculatePrice(from, to, route) * seat;
				RazorpayClient razorpay = null;
				try {
					razorpay = new RazorpayClient("rzp_test_f4vcAPoh0RDZfi", "jjblWSJ6F7NJuPUOtNmDjg4i");
					JSONObject orderRequest = new JSONObject();
					orderRequest.put("amount", price * 100);
					orderRequest.put("currency", "INR");

					Order order = razorpay.orders.create(orderRequest);

					TripOrder tripOrder = new TripOrder();
					tripOrder.setFrom(from);
					tripOrder.setTo(to);
					tripOrder.setAmount(price);
					tripOrder.setBookingDate(LocalDate.now());
					tripOrder.setArrivalTime(calculator.timeCalculator(to, route));
					tripOrder.setDepartureTime(calculator.timeCalculator(from, route));
					tripOrder.setOrderId(order.get("id"));
					tripOrder.setSeat(seat);
					tripOrder.setBusId(bus.getId());

					orderRepository.save(tripOrder);

					customer.getTripOrders().add(tripOrder);
					customerDao.save(customer);
					map.put("tripOrder", tripOrder);
					map.put("key", "rzp_test_f4vcAPoh0RDZfi");
					map.put("customer", customer);
					
					session.setAttribute("customer", customerRepository.findById(customer.getId()).orElseThrow());
					session.setAttribute("successMessage", "Check Details and Do Payment");
					return "razor-pay.html";

				} catch (RazorpayException e) {
					e.printStackTrace();
					session.setAttribute("failMessage", "Payment Failed");
					return "redirect:/";
				}
			} else {
				session.setAttribute("failMessage", "Sorry! Tickets are Not Available");
				return "redirect:/";
			}

		}
	}

	public String confirmOrder(int id, String razorpay_payment_id, HttpSession session) {
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			session.setAttribute("failMessage", "First Login to Book");
			return "redirect:/login";
		} else {
			TripOrder order = orderRepository.findById(id).orElseThrow();
			order.setPaymentId(razorpay_payment_id);
			orderRepository.save(order);

			Bus bus = busRepository.findById(order.getBusId()).orElseThrow();
			bus.setSeat(bus.getSeat() - order.getSeat());

			busRepository.save(bus);

			session.setAttribute("successMessage", "Ticket booked Successfully");
			return "redirect:/";
		}
	}

}
