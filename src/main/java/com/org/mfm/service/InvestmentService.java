package com.org.mfm.service;

import java.util.List;

import com.org.mfm.dto.TransactionRequest;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

import jakarta.validation.Valid;

public interface InvestmentService {

	List<?> findAllByFolioNumber(int folioNumber);

	List<?> getInvestments(InvestmentType stock, Integer folioNumber);

	Transaction updateInvestment(Transaction stockTxn, Investment investment);

	Transaction addInvestment(Transaction stockTxn, PortFolio port);

	void updateInvestmentTransaction(PortFolio port, Transaction txn);

	Transaction mapToEntityTransaction(@Valid TransactionRequest txnRequest);

}
