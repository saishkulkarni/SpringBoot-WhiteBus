package com.s13sh.white_bus.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Component
@Entity
public class Agency {
	@Id
	@GeneratedValue(generator = "a_id")
	@SequenceGenerator(initialValue = 121001, allocationSize = 1, name = "a_id")
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
	@NotEmpty(message = "* this is Required field")
	private String gst_no;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "* Password should contain minimum 8 chareecter, inlcude one upper case, lowercase , number and special charecter")
	private String password;
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "* Password should contain minimum 8 chareecter, inlcude one upper case, lowercase , number and special charecter")
	private String cpassword;
	@NotEmpty(message = "* this is Required field")
	private String reg_no;
	@NotEmpty(message = "* this is Required field")
	private String pan_no;
	private int otp;
	private boolean status;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Bus> buses = new ArrayList<Bus>();

}
