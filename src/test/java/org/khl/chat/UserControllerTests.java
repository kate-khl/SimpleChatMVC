package org.khl.chat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khl.chat.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts="classpath:data.sql")
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
    
	@Test
	public void createUser() throws Exception {
    	mokMvcWithoutFilters.perform(post("/registration")	
				.content("{\r\n\"id\":null,\"name\":\"Тест\",\"email\":\"user0@test.com\",\"password\":\"123\",\"role\":\"user\"\r\n}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}
	
	@Test
	public void authorization() throws Exception {
		mokMvcWithoutFilters.perform(post("/auth")	
				.content("{\"email\" : \"user4@test.com\",\"password\" : \"123\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void unauthorization() throws Exception {
		mokMvcWithoutFilters.perform(post("/auth")	
				.content("{\"email\" : \"user4555@test.com\",\"password\" : \"123\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void removeUser() throws Exception {
		mockMvc.perform(delete("/users/{id}", 1002)
			.header("Authorization", tokenService.getToken("user4@test.com", "123")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void readAllUsers() throws Exception {
		mockMvc.perform(get("/users/list")
			.header("Authorization", tokenService.getToken("user2@test.com", "123"))
)   
		.andExpect(status().isOk());
	}
	
	@Test
	public void findUserById() throws Exception {
		mockMvc.perform(get("/users/{id}", 1001)
			.header("Authorization", tokenService.getToken("user2@test.com", "123")))
		.andExpect(status().isOk());
	}
	
	@Test
	public void editUser() throws Exception {
		mockMvc.perform(patch("/users/{id}", 1002)
			.header("Authorization", tokenService.getToken("user2@test.com", "123"))
			.content("{\"id\" : \"1002\",\"name\" : \"Петя\",\"email\" : \"user3@test.com\",\"role\" : \"user\"}")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void getUsersfromChat() throws Exception {		
		mockMvc.perform(get("/users/chats/{id}",1004)
			.header("Authorization", tokenService.getToken("user1@test.com", "123")))
		.andExpect(status().isOk());
	}
	

	@Test
	public void findUser() throws Exception {
		mockMvc.perform(get("/users")
			.header("Authorization", tokenService.getToken("user2@test.com", "123"))
	        .param("name", "UserName1"))
		.andExpect(status().isOk());
	}
	
}
