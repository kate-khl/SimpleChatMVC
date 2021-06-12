package org.khl.chat;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

public class Session {

	private Long id;
	private String name;
	private String email;
	
	private Session() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String role;
	

	
	public static Session fromToken(String token) {
		if(token == null) return new Session();
		token = token.replace("_", "+").replace("-", "/");
		
		String[] split_string = token.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];		
        System.out.println(base64EncodedHeader);
        System.out.println(base64EncodedBody);
        
        String body = new String(Base64.getMimeDecoder().decode(base64EncodedBody));
        Gson gson = new Gson();
        Session ss = gson.fromJson(body, Session.class);
        
        return ss;
	}
	
}