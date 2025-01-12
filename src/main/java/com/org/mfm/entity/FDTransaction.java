package com.org.mfm.entity;

import java.sql.Date;

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
public class FDTransaction extends Transaction {

	private String name;
	private Date maturityDate;
	private float rateOfInt;
	private int compoundingFrequency;

}
