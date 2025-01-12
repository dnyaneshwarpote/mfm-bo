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
	private String name;
	private float interestRate;
	private int investmentYear;

}
