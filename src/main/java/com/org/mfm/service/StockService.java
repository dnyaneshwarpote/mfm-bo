package com.org.mfm.service;

import com.org.mfm.dto.TransactionRequest;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;

import jakarta.validation.Valid;

public interface StockService extends InvestmentService {

	void updateStockInvestment(StockTransaction stockTxn, Investment investment);

	@Override
	Transaction mapToEntityTransaction(@Valid TransactionRequest txnRequest);

}
