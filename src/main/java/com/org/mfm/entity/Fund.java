package com.org.mfm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Fund extends Investment {

	private double averagePrice;
	private String fundName;
	private float heldQuantity;
	@Id
	private int id;
	private float nav;
	private float quantity;

}
