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
public class MessageControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
    private WebApplicationContext context;
    @Autowired
    private TokenService tokenService;

    @Test
    public void sendMessage() throws Exception{
    	mockMvc.perform(post("/chats/{id}/messages", 1004)
    	.header("Authorization", tokenService.getToken("user1@test.com", "123"))
		.content("{\"value\" : \"msg\"}")
		.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
    	
    }
    
    @Test
    public void deleteMessage() throws Exception{
    	mockMvc.perform(delete("/messages/{id}", 1100)
    			.header("Authorization", tokenService.getToken("user1@test.com", "123")))
    	.andExpect(status().isOk());
    	
    }
    
    @Test
    public void editMessage() throws Exception{
    	mockMvc.perform(post("/messages/{id}", 1100)
    	.header("Authorization", tokenService.getToken("user1@test.com", "123"))
		.content("{\"value\" : \"msg_new\"}")
		.contentType(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
    	
    }
    
    @Test
    public void getMessagesFromChat() throws Exception{
    	mockMvc.perform(get("/chats/{id}/messages", 1004)
			.header("Authorization", tokenService.getToken("user1@test.com", "123"))
	        .param("page", "1")
	        .param("size", "5") )   
    	.andExpect(status().isOk());
    	
    }
    
}
