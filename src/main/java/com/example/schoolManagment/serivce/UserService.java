package com.example.schoolManagment.serivce;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.schoolManagment.entity.User;
import com.example.schoolManagment.entity.enums.Role;
import com.example.schoolManagment.repo.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean createUser(User user) {
		String username = user.getUsername();

		if (userRepository.findByUsername(username) != null) {
			return false;
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
    	user.getRoles().add(Role.ROLE_USER);

		log.info("Saving new User with username : {}", username);
		
		userRepository.save(user);

		return true;
	}
	
	public List<User> userList() {
		return userRepository.findAll();
	}
	
	public boolean deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			return false;
		}
		
		userRepository.deleteById(id);

		return true;
	}
	
	public List<User> findUserById(Long id) {
		List<User> res = new ArrayList<>();
		
		if (!userRepository.existsById(id)) {
			return null;
		}
		
		Optional<User> user = userRepository.findById(id);
		user.ifPresent(res::add);
		
		return res;
		
	}
	
	public boolean editUser(User user) {
		String username = user.getUsername();
		
		if (userRepository.findByUsername(username) != null) {
			return false;
		}
		
		user.setUsername(user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEmail(user.getEmail());
		
		userRepository.save(user);
		
		log.info("Edit new User with username : {}", username);
		
		return true;
		
	}
}
