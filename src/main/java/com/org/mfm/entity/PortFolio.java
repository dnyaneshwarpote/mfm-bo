package com.org.mfm.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PortFolio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int folioNumber;

	private String tittle;

	private double totalGain;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "porfolio", cascade = CascadeType.ALL)
	private List<Investment> investments;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "porfolio", cascade = CascadeType.ALL)
	private List<Liability> liabilities;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "investor_id", nullable = false)
	private Investor investor;

}
