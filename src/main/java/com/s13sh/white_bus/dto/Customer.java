package com.s13sh.white_bus.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Component
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 3, max = 30, message = "* Enter between 3 to 30 charecters")
	private String name;
	@NotEmpty(message = "* this is Required field")
	private String address;
	@DecimalMax(value = "9999999999", message = "* Enter Proper Mobile Number")
	@DecimalMin(value = "6000000000", message = "* Enter Proper Mobile Number")
	private long mobile;
	@NotEmpty(message = "* this is Required field")
	@Email(message = "* Enter Proper Email")
	private String email;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "* Password should contain minimum 8 chareecter, inlcude one upper case, lowercase , number and special charecter")
	private String password;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "* Password should contain minimum 8 chareecter, inlcude one upper case, lowercase , number and special charecter")
	private String cpassword;
	@Past(message = "* Enter proper Dob")
	@NotNull(message = "* this is Required field")
	private LocalDate dob;
	@NotEmpty(message = "* this is Required field")
	private String gender;
	private int otp;
	boolean status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<TripOrder> tripOrders = new ArrayList<TripOrder>();

}
