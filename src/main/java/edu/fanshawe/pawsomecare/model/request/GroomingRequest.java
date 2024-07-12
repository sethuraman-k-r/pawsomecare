package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GroomingRequest {

	private Integer id;
	private String name;
	private String description;
	private double cost = 0.0d;
	private Integer timeRequire;
	private Boolean isInsAllowed;
	
}
