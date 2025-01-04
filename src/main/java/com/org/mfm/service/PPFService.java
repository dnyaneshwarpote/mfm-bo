package com.org.mfm.service;

import com.org.mfm.dto.TransactionRequest;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PPFTransaction;

import jakarta.validation.Valid;

public interface PPFService extends InvestmentService {

	void updatePPFInvestment(PPFTransaction ppfTxn, Investment investment);

	@Override
	PPFTransaction mapToEntityTransaction(@Valid TransactionRequest txnRequest);

}
