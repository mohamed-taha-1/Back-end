package com.model.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.model.UserEntity;
import com.model.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
	UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

	
	UserDto entityToDto(UserEntity entity);

	@Mapping(target = "id", ignore = true)
	UserEntity dtoToEntity(UserDto user);
}
