package com.s13sh.white_bus.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class Station {

	int id;
	String name;
	LocalTime time;
	double price;
}
