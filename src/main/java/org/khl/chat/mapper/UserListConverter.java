package org.khl.chat.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserListConverter  extends AbstractConverter<Collection<User>, Collection<UserDto>>{

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Collection<UserDto> convert(Collection<User> source) {
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		ArrayList<UserDto> uDtos = modelMapper.map(source, listType);
		return uDtos;
	}
}
