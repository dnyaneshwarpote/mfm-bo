package com.org.mfm.service;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.mfm.dto.AuthenticationResponse;
import com.org.mfm.dto.UserDto;
import com.org.mfm.dto.mapper.UserDtoMapper;
import com.org.mfm.entity.JwtToken;
import com.org.mfm.entity.User;
import com.org.mfm.enums.TokenType;
import com.org.mfm.repository.TokenRepository;
import com.org.mfm.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final UserDtoMapper userDtoMapper;

	public AuthenticationResponse authenticate(UserDto request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.userName(), request.password()));
		User user = repository.findByUserName(request.userName()).orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		AuthenticationResponse res = new AuthenticationResponse();
		res.setAccessToken(jwtToken);
		res.setRefreshToken(refreshToken);

		return res;
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userName;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userName = jwtService.extractUsername(refreshToken);
		if (userName != null) {
			var user = this.repository.findByUserName(userName).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				AuthenticationResponse authResponse = new AuthenticationResponse();
				authResponse.setAccessToken(accessToken);
				authResponse.setRefreshToken(refreshToken);

				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	public AuthenticationResponse register(UserDto request) {
		User user = this.userDtoMapper.toEntity(request);
		user.setPassword(passwordEncoder.encode(request.password()));
		var savedUser = repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);

		AuthenticationResponse res = new AuthenticationResponse();
		res.setAccessToken(jwtToken);
		res.setRefreshToken(refreshToken);
		return res;
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	private void saveUserToken(User user, String jwtToken) {
		JwtToken token = new JwtToken();
		token.setUser(user);
		token.setToken(jwtToken);
		token.setTokenType(TokenType.BEARER);
		token.setExpired(false);
		token.setRevoked(false);

		tokenRepository.save(token);
	}
}
