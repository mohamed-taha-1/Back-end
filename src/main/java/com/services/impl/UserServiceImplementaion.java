package com.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exceptions.UserServiceException;
import com.model.RoleEntity;
import com.model.UserEntity;
import com.model.dao.RoleRepository;
import com.model.dao.UserEntityRepository;
import com.model.dto.UserDto;
import com.model.dto.mappers.UserEntityMapper;
import com.security.UserPrincipal;
import com.services.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImplementaion implements UserService {

	private final UserEntityRepository userRepo;
	private final UserEntityMapper userMapper;

	private final BCryptPasswordEncoder encode;

	private final RoleRepository roleRepository;

	public UserServiceImplementaion(UserEntityRepository userRepo, RoleRepository roleRepository,
			UserEntityMapper userMapper, BCryptPasswordEncoder encode) {
		super();
		this.userRepo = userRepo;
		this.userMapper = userMapper;
		this.encode = encode;
		this.roleRepository = roleRepository;
	}

	/**
	@PostConstruct
	public void insertDummyUsersForFutureLogin() {

		if (userRepo.count() <= 4) {
			UserEntity user1 = new UserEntity();
			user1.setEmail("ahmed@gmail.com");
			user1.setPassword(encode.encode("1234"));

			UserEntity user2 = new UserEntity();
			user2.setEmail("yaser@gmail.com");
			user2.setPassword(encode.encode("1234"));

			UserEntity user3 = new UserEntity();
			user3.setEmail("ismail@gmail.com");
			user3.setPassword(encode.encode("1234"));

			UserEntity user4 = new UserEntity();
			user4.setEmail("shahin@gmail.com");
			user4.setPassword(encode.encode("1234"));

			UserEntity[] allUsers = { user1, user2, user3, user4 };
			for (UserEntity userIndex : allUsers) {
				userRepo.save(userIndex);
			}
		}
	}
**/
	@Override
	@Transactional
	public void saveNewUser(UserDto userDto) {
		if (userRepo.findByEmail(userDto.getEmail()) != null)
			throw new UserServiceException("Record already exists");

		UserEntity userEntity = userMapper.dtoToEntity(userDto);
		// Set roles
		Collection<RoleEntity> roleEntities = new HashSet<>();
		for (RoleEntity role : userDto.getRoles()) {
			RoleEntity roleEntity = roleRepository.findByName(role.toString());
			if (roleEntity != null) {
				roleEntities.add(roleEntity);
			}
		}

		userEntity.setRoles(roleEntities);

		userRepo.save(userEntity);
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		returnValue = userMapper.entityToDto(userEntity);

		return returnValue;
	}

	// --- user details


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepo.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		
		return new UserPrincipal(userEntity);
	}
}
