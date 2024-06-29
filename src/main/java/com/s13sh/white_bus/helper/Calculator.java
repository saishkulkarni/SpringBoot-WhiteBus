package com.s13sh.white_bus.helper;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.s13sh.white_bus.dto.Route;
import com.s13sh.white_bus.dto.Station;

@Service
public class Calculator {

	public double calculatePrice(String from, String to, Route route) {
		double fromPrice = 0;
		double toPrice = 0;
		for (Station station : route.getStations()) {
			if (station.getName().equals(from)) {
				fromPrice = station.getPrice();
			}
			if (station.getName().equals(to)) {
				toPrice = station.getPrice();
			}
		}

		return toPrice - fromPrice;
	}

	public LocalTime timeCalculator(String location, Route route) {
		for (Station station : route.getStations()) {
			if (station.getName().equals(location)) {
				return station.getTime();
			}
		}
		return null;
	}
}
