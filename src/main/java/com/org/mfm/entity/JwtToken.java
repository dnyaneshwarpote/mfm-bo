package com.org.mfm.entity;

import com.org.mfm.enums.TokenType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class JwtToken {

	private boolean expired;

	@Id
	@GeneratedValue
	public Integer id;

	private boolean revoked;

	@Column(unique = true)
	public String token;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	public TokenType tokenType = TokenType.BEARER;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User user;

}
