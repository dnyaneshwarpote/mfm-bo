package com.org.mfm.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class MfmUser {
	
	@Id
	@GeneratedValue
	private int userId;
	private String userName;
	private String password;

}
