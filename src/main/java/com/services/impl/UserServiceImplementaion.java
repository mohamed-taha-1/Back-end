package com.services.impl;

import org.springframework.stereotype.Service;
import com.model.UserEntity;
import com.model.dao.UserEntityRepository;
import com.model.dto.UserDto;
import com.model.dto.mappers.UserEntityMapper;
import com.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImplementaion implements UserService {

	private final UserEntityRepository userRepo;
	private final UserEntityMapper userMapper;

	private final BCryptPasswordEncoder encode;

	public UserServiceImplementaion(UserEntityRepository userRepo, UserEntityMapper userMapper,
			BCryptPasswordEncoder encode) {
		super();
		this.userRepo = userRepo;
		this.userMapper = userMapper;
		this.encode = encode;
	}

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

	@Override
	@Transactional
	public void saveNewUser(UserDto userDto) {
		String email = userDto.getEmail();
		UserEntity userTest = userRepo.findByEmail(email);
		if (userTest != null)
			throw new RuntimeException("Try with anther email");
		UserDto local = new UserDto();
		local.setEmail(userDto.getEmail());
		local.setPassword(encode.encode(userDto.getPassword()));

		UserEntity user = userMapper.dtoToEntity(local);
		userRepo.save(user);

	}

	// --- user details

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByEmail(username);
		if (user == null)
			throw new UsernameNotFoundException("Try with anther email");

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
	}
}
