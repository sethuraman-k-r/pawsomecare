package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VaccineRequest {

	private Integer id;
	private String name;
	private String description;
	private double amount = 0.0d;
	private Boolean isInsAllowed;
	
}
