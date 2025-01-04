package com.org.mfm.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm.dto.AuthenticationRequest;
import com.org.mfm.dto.UserRequest;
import com.org.mfm.response.AuthenticationResponse;
import com.org.mfm.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Controller for New User Registration, Authentication and Authorization")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private AuthenticationService service;

	@Operation(description = "User Activation", summary = "Activate the newly created User", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@PutMapping("/activate")
	public ResponseEntity<AuthenticationResponse> activate(@RequestBody UserRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

	@Operation(description = "Get registered User", summary = "Get registered User", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/get")
	public ResponseEntity<AuthenticationResponse> getUser(@RequestBody UserRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@Operation(description = "Get the list of all registered Users", summary = "List of registered Users", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/get-all")
	public ResponseEntity<AuthenticationResponse> getUsers(@RequestBody UserRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		service.refreshToken(request, response);
	}

	@Operation(description = "New User Creation", summary = "Create new user", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@PostMapping("/create")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody UserRequest request) {
		return ResponseEntity.ok(service.register(request));
	}

}