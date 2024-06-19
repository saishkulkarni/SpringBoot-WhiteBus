package com.s13sh.white_bus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.white_bus.dto.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {

	List<Route> findByBus_idIn(List<Integer> list);

}
