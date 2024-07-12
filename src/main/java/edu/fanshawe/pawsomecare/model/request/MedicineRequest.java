package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MedicineRequest {

	private Integer id;
	private String name;
	private String description;
	private double cost = 0.0d;
	private Integer count = 0;
	private Boolean isInsAllowed;
	private Date expiresAt;
	
}
