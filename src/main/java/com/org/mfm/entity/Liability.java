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
	private double currentValue;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double investmentValue;
	private LiabilityType liabilityType;
	private double netProfit;

	@ManyToOne
	@JoinColumn(name = "liability_Id", nullable = false)
	private PortFolio porfolio;

}
