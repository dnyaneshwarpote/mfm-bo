package com.org.mfm.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Investor {

	@Id
	private String userName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "investor")
	private List<PortFolio> folios = new ArrayList<>();

}
