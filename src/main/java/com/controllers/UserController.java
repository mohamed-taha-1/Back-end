package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.dto.UserDto;
import com.services.impl.UserServiceImplementaion;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServiceImplementaion userService;

	@PostMapping("/save")
	public ResponseEntity<?> saveuser(@Valid @RequestBody UserDto userDto) {
		userService.saveNewUser(userDto);
		return ResponseEntity.ok().build();
	}
}
