package com.org.mfm.dto;

import com.org.mfm.enums.InvestmentType;

public record InvestmentResponse(InvestmentType investmentType,

		double investmentValue,

		double dayGain,

		double currentValue,

		double totalProfit,

		int folioNumber,

		double investmentPercentage) {

}