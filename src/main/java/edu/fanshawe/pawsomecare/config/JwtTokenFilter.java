package edu.fanshawe.pawsomecare.config;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.fanshawe.pawsomecare.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	private final UserRepository userRepository;

	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (isEmpty(header) || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		final String token = header.split(" ")[1].trim();
		if (!jwtTokenUtil.validate(token)) {
			chain.doFilter(request, response);
			return;
		}

		// Get user identity and set it on the spring security context
		UserDetails userDetails = Optional.ofNullable(userRepository.findByEmail(jwtTokenUtil.getUsername(token)))
				.get();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails == null ? Arrays.asList() : userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}
