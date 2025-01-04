package com.org.mfm.dto;

import java.sql.Date;

import com.org.mfm.enums.InvestmentType;
import com.org.mfm.enums.TransactionType;

public record TransactionResponse(int folioNumber, int txnId, Date txnDate, double txnAmount, InvestmentType invType,
		TransactionType txnType, String fundName, String stockName, String institutionName, float quantity, float nav,
		float rate, float brokerage) {

}