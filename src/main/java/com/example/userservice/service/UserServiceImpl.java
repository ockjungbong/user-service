package com.example.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor	// final이나 @Notnull 어노테이션 붙은 필드의 생성자를 자동 생성해 주는 어노테이션.
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		
		// UserDto를 UserEntity로 변환
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
		
		userRepository.save(userEntity);
		
		UserDto returnUserDto = mapper.map(userEntity, UserDto.class);
		
		return returnUserDto;
	}

	@Override
	public UserDto getUserById(String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
		List<ResponseOrder> orders = new ArrayList<>();
		userDto.setOrders(orders);
		
		return userDto;
	}

	@Override
	public Iterable<UserEntity> getUserByAll() {
		return userRepository.findAll();
	}
	

}
