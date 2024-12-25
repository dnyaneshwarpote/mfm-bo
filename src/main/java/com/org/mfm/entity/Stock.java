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

	@Id
	private int id;
	private String stockName;
	private int heldQuantity;
	private double averagePrice;
	private double realizedProfit;

}
