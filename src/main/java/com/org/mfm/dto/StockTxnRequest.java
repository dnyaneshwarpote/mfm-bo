package com.org.mfm.dto;

import java.sql.Date;

import com.org.mfm.enums.TransactionType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StockTxnRequest(@NotBlank(message = "Please provide valid Stock name") String stockName,
		@Min(1) int quantity, float rate, float brokerage, int txnId, Date txnDate, double txnAmount,
		TransactionType txnType, @NotNull int folioNumber) {

}
