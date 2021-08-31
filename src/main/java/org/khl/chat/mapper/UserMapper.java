package org.khl.chat.mapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.khl.chat.dto.RegistrationUserRequest;
import org.khl.chat.dto.UserDto;
import org.khl.chat.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	@Autowired
	private ModelMapper mapper;

	public User toEntity(UserDto dto) {
		return Objects.isNull(dto) ? null : mapper.map(dto, User.class);
	}

	public User RegistrationUserRequestToEntity(RegistrationUserRequest dto) {
		return Objects.isNull(dto) ? null : mapper.map(dto, User.class);
	}

	public UserDto toDto(User entity) {

		return Objects.isNull(entity) ? null : mapper.map(entity, UserDto.class);
	}

	public Collection<UserDto> toListOfDto(Collection<User> source) {
		Type listType = new TypeToken<List<UserDto>>() {
		}.getType();
		ArrayList<UserDto> uDtos = mapper.map(source, listType);
		return uDtos;
	}

}
