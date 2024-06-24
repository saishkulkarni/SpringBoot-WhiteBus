package com.s13sh.white_bus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.s13sh.white_bus.dto.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {

	List<Route> findByBus_idIn(List<Integer> list);

	@Query("SELECT r FROM Route r WHERE r.saturday = true AND :from MEMBER OF r.stations AND :to MEMBER OF r.stations")
	List<Route> findRouteByStationsOnSaturday(String from, String to);

	@Query("SELECT r FROM Route r WHERE r.monday = true AND :from MEMBER OF r.stations AND :to MEMBER OF r.stations")
	List<Route> findRouteByStationsOnMonday(String from, String to);

	@Query("SELECT r FROM Route r WHERE r.tuesday = true AND :from MEMBER OF r.stations AND :to MEMBER OF r.stations")
	List<Route> findRouteByStationsOnTuesday(String from, String to);

	@Query("SELECT r FROM Route r WHERE r.wednesday = true AND :from MEMBER OF r.stations AND :to MEMBER OF r.stations")
	List<Route> findRouteByStationsOnWednesday(String from, String to);

	@Query("SELECT r FROM Route r WHERE r.friday = true AND :from MEMBER OF r.stations AND :to MEMBER OF r.stations")
	List<Route> findRouteByStationsOnFriday(String from, String to);

	@Query("SELECT r FROM Route r WHERE r.sunday = true AND :from MEMBER OF r.stations AND :to MEMBER OF r.stations")
	List<Route> findRouteByStationsOnSunday(String from, String to);

	@Query("SELECT r FROM Route r WHERE r.thursday = true AND :from MEMBER OF r.stations AND :to MEMBER OF r.stations")
	List<Route> findRouteByStationsOnThursday(String from, String to);

}
