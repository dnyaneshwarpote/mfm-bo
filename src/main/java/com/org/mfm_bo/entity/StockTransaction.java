package com.org.mfm_bo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class StockTransaction {

	@Id
	@GeneratedValue
	long txnId;
	String securityName;
	float quantity;
	float rate;
	double txnAmount;
	float charges;

}
