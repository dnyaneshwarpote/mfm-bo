package com.org.mfm.service;

import java.util.List;

import com.org.mfm.dto.StockTxnRequest;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

import jakarta.validation.Valid;

public interface InvestmentService {

	List<?> findAllByFolioNumber(int folioNumber);

	List<?> getInvestments(InvestmentType stock, Integer folioNumber);

	StockTransaction updateInvestment(Transaction stockTxn, Investment investment);

	StockTransaction addInvestment(Transaction stockTxn, PortFolio port);

	void updateInvestmentTransaction(PortFolio port, StockTransaction stockTxn);

	StockTransaction mapToEntityTransaction(@Valid StockTxnRequest txnRequest);

}
