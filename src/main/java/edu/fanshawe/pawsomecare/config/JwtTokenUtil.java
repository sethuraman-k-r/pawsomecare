package edu.fanshawe.pawsomecare.config;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import edu.fanshawe.pawsomecare.model.User;

@Component
public class JwtTokenUtil {

	private static final String SECRET = "2sadf@3qasdg";

	public String generateAccessToken(User user) throws Exception {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			String token = JWT.create().withClaim("user", user.getEmail()).withIssuer("auth0").sign(algorithm);
			return token;
		} catch (JWTCreationException exception) {
			throw new Exception("Error in creating the JWT token");
		}
	}

	public boolean validate(String token) {
		return getJwt(token).isPresent();
	}

	private Optional<DecodedJWT> getJwt(String token) {
		DecodedJWT decodedJWT;
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();

			decodedJWT = verifier.verify(token);

			return Optional.of(decodedJWT);
		} catch (JWTVerificationException exception) {
			return Optional.empty();
		} catch (Exception ex) {
			return Optional.empty();
		}
	}

	public String getUsername(String token) {
		DecodedJWT decodedJWT = getJwt(token).orElse(null);
		if (decodedJWT != null) {
			return decodedJWT.getClaim("user").asString();
		}
		return null;
	}

}
