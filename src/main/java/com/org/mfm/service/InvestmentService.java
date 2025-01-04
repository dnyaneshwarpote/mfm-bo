package com.org.mfm.service;

import java.util.List;

import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

public interface InvestmentService {

	Transaction addInvestment(Transaction stockTxn, PortFolio port);

	List<?> findAllByFolioNumber(int folioNumber);

	List<?> getInvestments(InvestmentType stock, Integer folioNumber);

	Transaction updateInvestment(Transaction stockTxn, Investment investment);

	void updateInvestmentTransaction(PortFolio port, Transaction txn);

}
