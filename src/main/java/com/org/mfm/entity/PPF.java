package com.org.mfm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PPF extends Investment {

	@Id
	private int id;
	private String fundName;
	private float quantity;
	private float nav;
	private float heldQuantity;
	private double averagePrice;

}
