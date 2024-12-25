package com.org.mfm.dto;

import java.sql.Date;

import com.org.mfm.enums.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FundTxnRequest(@NotBlank(message = "Please provide valid Stock name") String fundName, float quantity,
		float nav, int txnId, Date txnDate, float txnAmount,
		@NotBlank(message = "Please provide valid Transction Type") TransactionType txnType, @NotNull int folioNumber) {

}