package com.org.mfm.entity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class StockTransaction extends Transaction {

	private float brokerage;
	private float quantity;
	private float rate;
	private String stockName;

}
