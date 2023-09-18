package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.model.UserEntity;
import com.model.dao.UserEntityRepository;
import com.model.dto.UserDto;
import com.model.dto.mappers.UserEntityMapper;
import com.services.impl.UserServiceImplementaion;
/**
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImplementaion userService;

	@Mock
	private UserEntityRepository userRepository;

	@Autowired
	private UserEntityMapper mapper;

	@Mock
	private BCryptPasswordEncoder encoder; // Corrected the name of the encoder

	UserEntity userEntity;
	 
	@BeforeEach
	void setUp() throws Exception {

		
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setEmail("test@test.com");
		userEntity.setPassword("1234");
	
	}
	
	@Test
	@DisplayName("Save new user test")
	public void testSaveUser() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDto userDto=new UserDto();
		assertNotNull(userDto);
		assertEquals("test@test.com", userDto.getEmail());
		userDto.setEmail("mihamed@gmail.com");
		userDto.setPassword("1234");
		userService.saveNewUser(userDto);
		
	}
}

*/