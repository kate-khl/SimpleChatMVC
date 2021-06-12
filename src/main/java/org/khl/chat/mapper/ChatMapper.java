package org.khl.chat.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.khl.chat.dto.ChatDto;
import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.Chat;
import org.khl.chat.entity.Message;
import org.khl.chat.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
	
    @Autowired
    private ModelMapper mapper;
    
    
    public Chat toEntity(ChatDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Chat.class);
    }


    public ChatDto toDto(Chat entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, ChatDto.class);
    }
    

	public Collection<ChatDto> toListOfDto(Collection<Chat> source) {
		Type listType = new TypeToken<List<ChatDto>>() {}.getType();
		ArrayList<ChatDto> cDtos = mapper.map(source, listType);
		return cDtos;
	}
}
