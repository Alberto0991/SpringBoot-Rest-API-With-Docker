package com.prova.springboot_rest_api_example.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.prova.springboot_rest_api_example.dto.UserDTO;
import com.prova.springboot_rest_api_example.model.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	public abstract User toModel(UserDTO userDTO);
	
	public abstract List<UserDTO> toDTOs(List<User> users);

}
