package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

	private String username;
	private String email;
	private String password;
	
}
