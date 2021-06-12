package org.khl.chat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class ChatControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
    private WebApplicationContext context;
    @Autowired
    private TokenService tokenService;
    
	@Test
	public void createChat() throws Exception {
		mockMvc.perform(post("/chats")	
			.header("Authorization", tokenService.getToken("user1@test.com", "123"))
			.content("{\"name\":\"chatName1\",\"userIds\": [1001],\"message\" : \"Привет\"}")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void addUsers() throws Exception {
		mockMvc.perform(post("/chats/{id}/users", 1004)	
			.header("Authorization", tokenService.getToken("user1@test.com", "123"))
			.content("[1002, 1003]")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	
	@Test public void getChats() throws Exception {
	mockMvc.perform(get("/user/{id}/chats", 1000)
			.header("Authorization", tokenService.getToken("user1@test.com", "123")))
	.andExpect(status().isCreated());
}
	
	@Test
	public void removeUsers() throws Exception {
		mockMvc.perform(delete("/chats/{id}/users", 1004)	
			.header("Authorization", tokenService.getToken("user1@test.com", "123"))
			.content("[1001]")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
}
