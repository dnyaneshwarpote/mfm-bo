package com.org.mfm.dto;

import com.org.mfm.enums.Role;

public record UserRequest(String firstName, String lastName, String userName, String email, String contact,
		String password, Role role) {

}