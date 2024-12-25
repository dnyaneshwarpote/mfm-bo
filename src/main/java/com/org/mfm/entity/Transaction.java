package com.org.mfm.entity;

import java.sql.Date;

import com.org.mfm.enums.TransactionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int txnId;
	private Date txnDate;
	private double txnAmount;
	private TransactionType txnType;
	private int folioNumber;

	@JoinColumn(name = "investment_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Investment investment;

}
