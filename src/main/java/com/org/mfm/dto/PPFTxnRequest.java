package com.org.mfm.dto;

import java.sql.Date;

import com.org.mfm.enums.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PPFTxnRequest(@NotBlank(message = "Please provide valid Institution name") String institutionName, int txnId,
		Date txnDate, double txnAmount, TransactionType txnType, @NotNull int folioNumber) {

}
