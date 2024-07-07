package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChangePasswordRequest {

	private final String oldPassword;
	private final String newPassword;
	private final String confirmPassword;
	
}
