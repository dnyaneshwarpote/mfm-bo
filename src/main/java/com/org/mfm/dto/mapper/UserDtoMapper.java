package com.org.mfm.dto.mapper;

import org.springframework.stereotype.Service;

import com.org.mfm.dto.UserDto;
import com.org.mfm.entity.User;

@Service
public class UserDtoMapper {

	public UserDto toDto(User user) {
		return new UserDto(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(),
				user.getContact(), user.getPassword(), user.getRole());

	}

	public User toEntity(UserDto userDto) {
		User user = new User();
		user.setFirstName(userDto.firstName());
		user.setLastName(userDto.lastName());
		user.setUserName(userDto.userName());
		user.setContact(userDto.contact());
		user.setEmail(userDto.email());
		user.setRole(userDto.role());
		return user;

	}

}
