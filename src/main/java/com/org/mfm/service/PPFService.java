package com.org.mfm.service;

import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PPFTransaction;

public interface PPFService extends InvestmentService {

	void updatePPFInvestment(PPFTransaction ppfTxn, Investment investment);

}
