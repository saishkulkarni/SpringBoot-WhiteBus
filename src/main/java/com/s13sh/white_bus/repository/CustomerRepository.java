package com.s13sh.white_bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.white_bus.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	Customer findByMobile(long mobile);

	Customer findByEmail(String email);

}
