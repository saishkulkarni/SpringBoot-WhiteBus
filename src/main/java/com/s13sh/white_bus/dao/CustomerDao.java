package com.s13sh.white_bus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.s13sh.white_bus.dto.Customer;
import com.s13sh.white_bus.repository.CustomerRepository;

@Repository
public class CustomerDao {

	@Autowired
	CustomerRepository customerRepository;

	public boolean checkEmail(String email) {
		return customerRepository.existsByEmail(email);
	}

	public boolean checkMobile(long mobile) {
		return customerRepository.existsByMobile(mobile);
	}

	public Customer findByMobile(long mobile) {
		return customerRepository.findByMobile(mobile);
	}

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

}
