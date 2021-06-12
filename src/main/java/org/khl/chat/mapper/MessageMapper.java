package org.khl.chat.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.khl.chat.dto.MessageDto;
import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.Message;
import org.khl.chat.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    @Autowired
    private ModelMapper mapper;
    
    public Message toEntity(MessageDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Message.class);
    }


    public MessageDto toDto(Message entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, MessageDto.class);
    }
    

	public Collection<MessageDto> toListOfDto(Collection<Message> source) {
		Type listType = new TypeToken<List<MessageDto>>() {}.getType();
		ArrayList<MessageDto> mDtos = mapper.map(source, listType);
		return mDtos;
	}
}
