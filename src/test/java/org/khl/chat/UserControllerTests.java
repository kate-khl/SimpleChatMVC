package org.khl.chat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khl.chat.common.Constant;
import org.khl.chat.common.Role;
import org.khl.chat.dto.UserDto;
import org.khl.chat.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:data.sql")
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MockMvc mokMvcWithoutFilters;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private TokenService tokenService;

	@BeforeEach
	public void setup() {
		this.mokMvcWithoutFilters = MockMvcBuilders.webAppContextSetup(context).build();
	}

//	@Test
//	public void createUser() throws Exception {
//    	mokMvcWithoutFilters.perform(post("/registration")	
//				.content("{\r\n\"id\":null,\"name\":\"Тест\",\"email\":\"user0@test.com\",\"password\":\"123\",\"role\":\"user\"\r\n}")
//				.contentType(MediaType.APPLICATION_JSON))
//			.andExpect(status().isCreated());
//	}
//	
//	@Test
//	public void authorization() throws Exception {
//		mokMvcWithoutFilters.perform(post("/auth")	
//				.content("{\"email\" : \"user4@test.com\",\"password\" : \"123\"}")
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk());
//	}
//	
//	@Test
//	public void unauthorization() throws Exception {
//		mokMvcWithoutFilters.perform(post("/auth")	
//				.content("{\"email\" : \"user4555@test.com\",\"password\" : \"123\"}")
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isUnauthorized());
//	}

	@Test
	public void removeUser() throws Exception {
		mockMvc.perform(delete("/chat/api/v1/users/{id}", 1002).cookie(new Cookie(Constant.JWT_TOKEN,
				tokenService.createToken(new UserDto(1004l, "UserName4", "user4@test.com", "123", Role.ROLE_USER)))))
				.andExpect(status().isOk());
	}

	@Test
	public void getUserById() throws Exception {
		mockMvc.perform(get("/chat/api/v1/users/{id}", 1001).cookie(new Cookie(Constant.JWT_TOKEN,
				tokenService.createToken(new UserDto(1002l, "UserName2", "user2@test.com", "123", Role.ROLE_USER)))))
				.andExpect(status().isOk());
	}

	@Test
	public void editUser() throws Exception {
		Gson gson = new Gson();
		mockMvc.perform(patch("/chat/api/v1/users/{id}", 1002)
				.cookie(new Cookie(Constant.JWT_TOKEN,
						tokenService
								.createToken(new UserDto(1002l, "UserName2", "user2@test.com", "123", Role.ROLE_USER))))
				.content(gson.toJson((new UserDto(1002l, "Name111", "user3@test.com", "123", Role.ROLE_USER)))) //"{\"id\" : \"1002\",\"name\" : \"Петя\",\"email\" : \"user3@test.com\",\"role\" : \"ROLE_USER\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

//	@Test
//	public void readAllUsers() throws Exception {
//		mockMvc.perform(get("/users/list")
//			.header("Authorization", tokenService.getToken("user2@test.com", "123")))   
//		.andExpect(status().isOk());
//	}

	@Test
	public void getUsersInChat() throws Exception {
		mockMvc.perform(get("/chat/api/v1/users/chats/{id}", 1004)
				.cookie(new Cookie(Constant.JWT_TOKEN,
						tokenService.createToken(new UserDto(1001l, "UserName1", "user1@test.com", "123", Role.ROLE_USER)))))
				.andExpect(status().isOk());
	}

	@Test
	public void getUserByName() throws Exception {
		mockMvc.perform(
				get("/chat/api/v1/users")
						.cookie(new Cookie(Constant.JWT_TOKEN,
								tokenService.createToken(
										new UserDto(1002l, "UserName2", "user2@test.com", "123", Role.ROLE_USER))))
						.param("name", "UserName1"))
				.andExpect(status().isOk());
	}

}
