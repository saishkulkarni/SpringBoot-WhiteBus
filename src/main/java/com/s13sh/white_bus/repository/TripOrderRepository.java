package com.s13sh.white_bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.white_bus.dto.TripOrder;

public interface TripOrderRepository extends JpaRepository<TripOrder, Integer> {

}
