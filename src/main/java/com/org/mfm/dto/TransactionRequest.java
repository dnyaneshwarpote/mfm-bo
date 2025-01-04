package com.org.mfm.dto;

import java.sql.Date;

import com.org.mfm.enums.InvestmentType;
import com.org.mfm.enums.TransactionType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(@NotNull int folioNumber, int txnId, Date txnDate, float txnAmount,
		InvestmentType invType, @NotBlank(message = "Please provide valid Transction Type") TransactionType txnType,
		String fundName, String stockName, String institutionName, float quantity, float nav, @Min(1) float rate,
		float brokerage) {

}