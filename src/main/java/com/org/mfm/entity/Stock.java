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
public class Stock extends Investment {

	private double averagePrice;
	private float heldQuantity;
	@Id
	private int id;
	private double realizedProfit;
	private String stockName;

}
