package com.org.mfm.dto;

import com.org.mfm.enums.Role;

import jakarta.validation.constraints.Email;

public record UserRequest(String firstName, String lastName, String userName, @Email String email, String contact,
		String password, Role role) {

}