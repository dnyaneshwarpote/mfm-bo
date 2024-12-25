package com.org.mfm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm.entity.MfmUser;
import com.org.mfm.repository.MfmUserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mfm-user")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class MfmUserController {

	@Autowired
	private MfmUserRepository userRepo;

	@Operation(description = "Generate/Create new Portfolio", summary = "Generate/Create new Portfolio", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
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