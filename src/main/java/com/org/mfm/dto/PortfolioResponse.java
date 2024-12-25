package com.org.mfm.dto;

import java.util.List;

public record PortfolioResponse(int folioNumber, String tittle, double networth, double totalGain,
		List<InvestmentResponse> investments, List<LiabilityResponse> liabilities) {

}