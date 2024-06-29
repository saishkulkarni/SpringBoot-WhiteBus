package com.s13sh.white_bus.helper;

import org.springframework.stereotype.Service;

import com.s13sh.white_bus.dto.Route;
import com.s13sh.white_bus.dto.Station;

@Service
public class PriceCalculator {

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
}
