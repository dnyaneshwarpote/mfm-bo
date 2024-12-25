package com.org.mfm.entity;

import java.util.List;

import com.org.mfm.enums.InvestmentType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Investment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double investmentValue;
	private double netProfit;
	private double currentValue;
	private double dayGain;

	@Enumerated(EnumType.STRING)
	private InvestmentType investmentType;

	@JoinColumn(name = "fk_porfolio", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PortFolio porfolio;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "investment")
	private List<Transaction> transactions;

}
