package com.org.mfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm.entity.MfmUser;
import com.org.mfm.repository.MfmUserRepository;

@RestController("/mfm-user")
public class MfmUserController {


	@Autowired
	private MfmUserRepository userRepo;

	@PostMapping("/register")
	public MfmUser registerUser(MfmUser user) {
		return userRepo.save(user);
	}
	
	@GetMapping("/all-users")
	public List<MfmUser> getAllUsers() {
		return userRepo.findAll();
	}
	
	@GetMapping("/greet")
	public String greet() {
		return "Greet!!!!!!!!!!!!!";
	}
	
}