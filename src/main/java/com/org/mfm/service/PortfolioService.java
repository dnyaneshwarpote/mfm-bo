package com.org.mfm.service;

import com.org.mfm.dto.PortfolioRequest;
import com.org.mfm.entity.PortFolio;

public interface PortfolioService {

	PortFolio createPortfolio(PortfolioRequest folioRequest);

	void deletePortFolio(int folioNumber);

	PortFolio getPortFolio(int folioNumber);

}
