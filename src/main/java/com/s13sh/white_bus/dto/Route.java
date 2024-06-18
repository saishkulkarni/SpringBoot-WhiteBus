package com.s13sh.white_bus.dto;

import java.util.List;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class Route {

	private int id;

	@OneToOne
	Bus bus;

	@OneToMany(fetch = FetchType.EAGER)
	List<Station> stations;

	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
}
