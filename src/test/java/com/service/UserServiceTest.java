package com.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.model.AuthorityEntity;
import com.model.RoleEntity;
import com.model.UserEntity;
import com.model.dao.AuthorityRepository;
import com.model.dao.RoleRepository;
import com.model.dao.UserEntityRepository;
import com.model.dto.UserDto;
import com.model.dto.mappers.UserEntityMapper;
import com.services.impl.UserServiceImplementaion;
import com.shared.Roles;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceTest {
	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

	@InjectMocks
	private UserServiceImplementaion userService;

	@Mock
	UserEntityRepository userRepository;

	@Mock
	UserEntityMapper userMapper;
	@Mock
	Roles roleEnm;
	@Mock
	RoleEntity roleEntity;
	@Mock
	RoleRepository roleRepository;

	@Mock
	AuthorityEntity authority;
	@Mock
	AuthorityRepository authorityRepository;

	@Mock
	UserEntity userEntity;
	UserDto userDto;

	@Mock
	private BCryptPasswordEncoder encoder; // Corrected the name of the encoder

	@BeforeEach
	void setUp() throws Exception {
		UserDto dto = new UserDto();
		dto.setEmail("admin@test.com");
		dto.setPassword(encoder.encode("1234"));

		RoleEntity role = new RoleEntity(Roles.ROLE_ADMIN.name());

		AuthorityEntity readAuthority = new AuthorityEntity("READ_AUTHORITY");
		authorityRepository.save(readAuthority);
		AuthorityEntity writeAuthority = new AuthorityEntity("WRITE_AUTHORITY");
		authorityRepository.save(writeAuthority);
		AuthorityEntity deleteAuthority = new AuthorityEntity("DELETE_AUTHORITY");
		authorityRepository.save(deleteAuthority);

		role.setAuthorities(Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
		roleRepository.save(role);

		dto.setRoles(Arrays.asList(role));
		userDto = dto;

	}

	@Test
	@DisplayName("Save_new_user")
	void testSaveUser() {
		assertNotNull(userDto);
		lenient().when(userRepository.save(any())).thenReturn(new UserEntity());

		userService.saveNewUser(userDto);

		verify(userMapper, times(1)).dtoToEntity(userDto);
		assertNotNull(userDto.getEmail());
		verify(userRepository, times(1)).save(any());

		log.info(":::::::::::: Sucess saved new user test :::::\n");
	}

	@Test
	@DisplayName("Find One user By Email")
	void testGetUser() {
		  when(userRepository.findByEmail(any())).thenReturn(new UserEntity());
	      userService.getUser(any());
	      verify(userRepository, times(1)).findByEmail(any());
	      log.info(":::::::::::: Sucess find one user by email test :::::\n");

	}
}
