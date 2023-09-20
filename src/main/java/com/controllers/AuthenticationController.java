package com.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.dto.UserDto;
 

@RestController
public class AuthenticationController {
	@PostMapping("/users/login")
	public void theFakeLogin(@RequestBody UserDto UserDto)
	{
		throw new IllegalStateException("This method should not be called. This method is implemented by Spring Security");
	}
}
