package com.s13sh.white_bus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.white_bus.dto.Station;

public interface StationRepository extends JpaRepository<Station, Integer> {

	List<Station> findByName(String from);

}
