package com.s13sh.white_bus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.s13sh.white_bus.dto.Agency;
import com.s13sh.white_bus.repository.AgencyRepository;

import jakarta.validation.Valid;

@Repository
public class AgencyDao {

	@Autowired
	AgencyRepository agencyRepository;
	
	public boolean checkEmail(String email) {
		return agencyRepository.existsByEmail(email);
	}

	public boolean checkMobile(long mobile) {
		return agencyRepository.existsByMobile(mobile);
	}

	public void save(Agency agency) {
		agencyRepository.save(agency);
	}
}
