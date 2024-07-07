package edu.fanshawe.pawsomecare.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.fanshawe.pawsomecare.config.JwtTokenUtil;
import edu.fanshawe.pawsomecare.model.User;
import edu.fanshawe.pawsomecare.model.request.AuthRequest;
import edu.fanshawe.pawsomecare.model.request.ChangePasswordRequest;
import edu.fanshawe.pawsomecare.model.request.UserRequest;
import edu.fanshawe.pawsomecare.services.UserDetailsService;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;

	public AuthController(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/public/login")
	public ResponseEntity<User> doLogin(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			User user = (User) userDetailsService.loadUserByUsername(authRequest.getEmail(), authRequest.getPassword());
			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
					.body(user);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/public/signup")
	public ResponseEntity<User> doSignup(@RequestBody @Validated AuthRequest authRequest) throws Exception {
		try {
			User user = (User) userDetailsService.createNewUser(authRequest);

			return ResponseEntity.ok().body(user);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/user/update")
	public ResponseEntity<User> doUpdateUser(@RequestBody UserRequest userRequest) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userDetailsService.updateUserDetails(user, userRequest);
			return ResponseEntity.ok(user);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/user/update/password")
	public ResponseEntity<User> doUpdatePassword(@RequestBody ChangePasswordRequest changeRequest) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userDetailsService.updateUserPassword(user, changeRequest);
			return ResponseEntity.ok(user);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
