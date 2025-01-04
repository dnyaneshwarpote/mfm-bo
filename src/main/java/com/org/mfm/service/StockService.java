package com.org.mfm.service;

import com.org.mfm.entity.Investment;
import com.org.mfm.entity.StockTransaction;

public interface StockService extends InvestmentService {

	void updateStockInvestment(StockTransaction stockTxn, Investment investment);

}
