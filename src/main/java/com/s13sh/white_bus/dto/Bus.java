package com.s13sh.white_bus.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private boolean ac;
	private boolean sleeper;
	private int seat;
	private String regno;
	private String imageLink;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Route> routes=new ArrayList<Route>();

}
