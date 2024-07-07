package edu.fanshawe.pawsomecare.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.fanshawe.pawsomecare.model.User;
import edu.fanshawe.pawsomecare.model.UserRole;
import edu.fanshawe.pawsomecare.model.request.AuthRequest;
import edu.fanshawe.pawsomecare.model.request.ChangePasswordRequest;
import edu.fanshawe.pawsomecare.model.request.UserRequest;
import edu.fanshawe.pawsomecare.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found with the email: " + username);
		} else {
			if (!passwordEncoder.matches(password, user.getPassword())) {
				throw new UsernameNotFoundException("Invalid credentials");
			}
			return user;
		}
	}

	public User createNewUser(AuthRequest authRequest) {
		User user = new User();
		user.setEmail(authRequest.getEmail());
		user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		user.setUsername(authRequest.getUsername());
		user.setFirstname(authRequest.getUsername());
		user.setRole(UserRole.USER);
		user.setCreatedAt(Timestamp.from(Instant.now()));
		user.setIsActive(true);
		user.setUpdatedOn(null);
		return userRepository.save(user);
	}

	public User updateUserDetails(User user, UserRequest userRequest) {
		if (userRequest.getFirstname() != null) {
			user.setFirstname(userRequest.getFirstname());
		}
		if (userRequest.getLastname() != null) {
			user.setLastname(userRequest.getLastname());
		}
		if (userRequest.getDob() != null) {
			user.setDob(Timestamp.from(userRequest.getDob().toInstant()));
		}
		if (userRequest.getIncome() != 0.0d) {
			user.setAnnualIncome(userRequest.getIncome());
		}
		if (userRequest.getAddress() != null) {
			user.setAddress(userRequest.getAddress());
		}
		if (userRequest.getContact() != null) {
			user.setContact(userRequest.getContact());
		}
		return userRepository.save(user);
	}

	public User updateUserPassword(User user, ChangePasswordRequest changeRequest) throws Exception {
		if (passwordEncoder.matches(changeRequest.getOldPassword(), user.getPassword())) {
			if (changeRequest.getNewPassword() != null && !StringUtils.isEmpty(changeRequest.getNewPassword().trim())
					&& changeRequest.getNewPassword().contentEquals(changeRequest.getConfirmPassword())) {
				user.setPassword(passwordEncoder.encode(changeRequest.getNewPassword()));
				return userRepository.save(user);
			}
			throw new Exception("Password not matched");
		}
		throw new Exception("Incorrect password");
	}

}
