package com.org.mfm.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MfmUser {
	
	@Id
	@GeneratedValue
	private int userId;
	private String userName;
	private String password;

}
