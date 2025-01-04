package com.org.mfm.service.impl;

import org.springframework.stereotype.Service;

import com.org.mfm.dao.UserRepository;
import com.org.mfm.entity.User;
import com.org.mfm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepo;

	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public User getUserByUserName(String userName) {
		return userRepo.findByUserName(userName).orElseThrow(() -> new RuntimeException("User details not found."));

	}
}
