package org.khl.chat.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.khl.chat.common.Constant;
import org.khl.chat.common.Role;
import org.khl.chat.dto.LoginRequestDto;
import org.khl.chat.dto.LoginResponseDto;
import org.khl.chat.dto.RegistrationUserRequest;
import org.khl.chat.dto.UserDto;
import org.khl.chat.service.TokenService;
import org.khl.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;

@CrossOrigin(origins = "http://127.0.0.1:8888")
@Controller
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class LoginController {

	private final UserService userService;
	private final TokenService tokenService;
	private final HttpServletResponse httpServletResponse;

	@Autowired
	public LoginController(@Qualifier("db") UserService userService, TokenService tokenService,
			HttpServletResponse httpServletResponse) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.httpServletResponse = httpServletResponse;
	}

	@GetMapping("/login")
	public String loginGet(Model model) {
		model.addAttribute("loginForm", new LoginRequestDto());
		return "/auth/login";
	}

//	@PostMapping("/login")
//	public String auth(LoginRequestDto requestDto) {
//		if (userService.checkLogin(requestDto.getEmail(), requestDto.getPassword())) {

//			String token = tokenService.getToken(requestDto.getEmail(), requestDto.getPassword());
//			httpServletResponse.addCookie(new Cookie(Constant.JWT_TOKEN, token));
//			return "redirect:/users/list";
//		} else
//			return "redirect:/login?error=true";
//	}

//	@GetMapping("/logout")
//	public String logout(Model model) {
//		Cookie cookie = new Cookie(Constant.JWT_TOKEN, null);
//		cookie.setMaxAge(0);
//		httpServletResponse.addCookie(cookie);
//		return "login";
//	}

	@PostMapping("/registration")
	public String create(@Valid RegistrationUserRequest user) {
		user.setRole(Role.ROLE_USER);
		userService.create(user);
		return "redirect:/login";
	}

	@GetMapping("/registration")
	public String createGet(Model model) {
		RegistrationUserRequest regForm = new RegistrationUserRequest();
		model.addAttribute("regForm", regForm);
		return "/auth/registration";
	}
}
