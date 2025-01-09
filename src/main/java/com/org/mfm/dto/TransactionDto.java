package com.org.mfm.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.enums.TransactionType;

import lombok.Builder;

@JsonInclude(Include.NON_NULL)
@Builder
public record TransactionDto(int folioNumber, int txnId, Date txnDate, double txnAmount, InvestmentType invType,
		TransactionType txnType, String fundName, String stockName, String institutionName, float nav, float quantity,
		float rate, float brokerage, float rateOfInt) {

}