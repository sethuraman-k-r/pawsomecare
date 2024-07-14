package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ClinicRequest {

	private Integer id;
	private String name;
	private String description;
	private String address;
	private String specialities;
	
}
