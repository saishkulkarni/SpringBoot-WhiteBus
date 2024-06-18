package com.s13sh.white_bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.white_bus.dto.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {

}
