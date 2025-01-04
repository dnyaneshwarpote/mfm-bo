package com.org.mfm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class PPF extends Investment {

	@Id
	private int id;
	private String institutionName;
	private float quantity;
	private float nav;
	private float heldQuantity;
	private double averagePrice;

}
