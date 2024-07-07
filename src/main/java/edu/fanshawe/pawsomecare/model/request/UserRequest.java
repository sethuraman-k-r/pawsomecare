package edu.fanshawe.pawsomecare.model.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

	private String firstname;
	private String lastname;
	private double income = 0.0d;
	private Date dob;
	private String address;
	private String contact;
	
}
