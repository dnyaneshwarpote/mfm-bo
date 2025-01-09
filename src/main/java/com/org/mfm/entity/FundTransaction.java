package com.org.mfm.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FundTransaction extends Transaction {

	private String fundName;
	private float nav;
	private float quantity;

}
