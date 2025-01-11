package com.org.mfm.service;

import com.org.mfm.entity.FDTransaction;
import com.org.mfm.entity.Investment;

public interface FDService extends InvestmentService {

	void updateFDInvestment(FDTransaction ppfTxn, Investment investment);

}
