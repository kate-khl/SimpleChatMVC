package org.khl.chat.service;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;

import org.khl.chat.Session;
import org.khl.chat.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;

@Service

public class TokenServiceImpl implements TokenService {

	@Autowired
	public TokenServiceImpl(@Qualifier("db") UserService userService) {
		this.userService = userService;
	}

	private UserService userService;
	private static final SignatureAlgorithm ALGORITM = SignatureAlgorithm.ES256;
	private static final String SECRET_KEY = "secretKey";

	@Override
	public String getToken(String email, String password) {

		UserDto userDto = new UserDto();
		userDto = userService.findUserByEmail(email);
		String jws = Jwts.builder().claim("email", userDto.getEmail())
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3 * 300)))
				.claim("id", userDto.getId()).claim("name", userDto.getName()).claim("role", userDto.getRole())
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
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		System.out.println(base64EncodedHeader);
		System.out.println(base64EncodedBody);

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

}
