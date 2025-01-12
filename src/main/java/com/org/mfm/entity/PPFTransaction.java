package com.org.mfm.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PPFTransaction extends Transaction {

	private double balanceAmount;
	private String name;
	private float rateOfInt;
}
