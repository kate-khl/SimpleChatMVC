package org.khl.chat.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.khl.chat.common.Role;
import org.khl.chat.dto.UserDto;
import org.khl.chat.security.CustomUserDetails;
import org.khl.chat.service.TokenService;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenServiceImpl implements TokenService {

	private static final SignatureAlgorithm ALGORITM = SignatureAlgorithm.ES256;
	private static final String SECRET_KEY = "secretKey";

	@Override
	public String createToken(UserDto userDto) {
		return asToken(userDto.getEmail(), userDto.getId(), userDto.getName(), userDto.getRole());
	}
	
	private String asToken(String email, Long id, String name, Role role) {
		String jws = Jwts.builder().claim("email", email)
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3 * 300)))
				.claim("id", id).claim("name", name).claim("role", role)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		return jws;
	}

	@Override
	public boolean verificationToken(String token) {
		if (token == null)
			return false;
		try {
			Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
			Date exp = claims.getExpiration();
			return true;

		} catch (ExpiredJwtException expiredJwtException) {
			System.out.println("Token Expires " + expiredJwtException);
			return false;
		}

		catch (Exception exception) {
			System.out.println("Exceptioin " + exception);
			return false;
		}
	}

	@Override
	public UserDto getUserFromToken(String token) {

		token = token.replace("_", "+").replace("-", "/");

		String[] split_string = token.split("\\.");
//		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];

		String body = new String(Base64.getMimeDecoder().decode(base64EncodedBody));
		Gson gson = new Gson();
		UserDto user = gson.fromJson(body, UserDto.class);

		return user;
	}

	@Override
	public boolean almostExpire(String token) {
		Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
		Date exp = claims.getExpiration();
		Date almostExp = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(300));
		if (exp.after(almostExp))
			return false;
		else
			return true;
	}

	@Override
	public CustomUserDetails asDetails(String token) {
		return verificationToken(token) ? CustomUserDetails.from(getUserFromToken(token)) : null;
	}

	@Override
	public String asToken(CustomUserDetails details) {
		return asToken(details.getEmail(), details.getId(), details.getUsername(), details.getRole());
	}

}