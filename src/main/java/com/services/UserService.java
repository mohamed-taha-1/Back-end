package com.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.model.dto.UserDto;

public interface UserService extends  UserDetailsService  {
	public void saveNewUser(UserDto userDto);
}
