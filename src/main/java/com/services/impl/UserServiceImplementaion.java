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

	@Override
	@Transactional
	public void saveNewUser(UserDto userDto) {
		if (userRepo.findByEmail(userDto.getEmail()) != null)
			throw new UserServiceException("Record already exists");

		UserEntity userEntity = userMapper.dtoToEntity(userDto);
		// Set roles
//		Collection<RoleEntity> roleEntities = new HashSet<>();
//		for (RoleEntity role : userDto.getRoles()) {
//			RoleEntity roleEntity = roleRepository.findByName(role.toString());
//			if (roleEntity != null) {
//				roleEntities.add(roleEntity);
//			}
//		}
//
//		userEntity.setRoles(roleEntities);

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
