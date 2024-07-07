package edu.fanshawe.pawsomecare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	private final JwtTokenFilter jwtTokenFilter;

	public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
		this.jwtTokenFilter = jwtTokenFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http = http.cors(t -> t.disable()).csrf(t -> t.disable());

		http = http.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http = http.exceptionHandling(t -> t.authenticationEntryPoint(
				(request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())));

		http.authorizeHttpRequests(t -> {
			t.requestMatchers("/auth/public/**").permitAll().anyRequest().authenticated();
		});

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
