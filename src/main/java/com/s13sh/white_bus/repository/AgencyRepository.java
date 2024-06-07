package com.s13sh.white_bus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.white_bus.dto.Agency;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

}
