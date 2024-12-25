package com.org.mfm.entity;

import com.org.mfm.enums.LiabilityType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Liability {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double investmentValue;
	private double netProfit;
	private double currentValue;
	private LiabilityType liabilityType;

	@ManyToOne
	@JoinColumn(name = "liability_Id", nullable = false)
	private PortFolio porfolio;

}
