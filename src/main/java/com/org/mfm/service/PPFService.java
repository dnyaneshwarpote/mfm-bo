package com.org.mfm.service;

import com.org.mfm.dto.StockTxnRequest;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.StockTransaction;

import jakarta.validation.Valid;

public interface PPFService extends InvestmentService {

	void updateStockInvestment(StockTransaction stockTxn, Investment investment);

	@Override
	StockTransaction mapToEntityTransaction(@Valid StockTxnRequest txnRequest);

}
