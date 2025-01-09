package com.org.mfm.service.impl;

import org.springframework.stereotype.Service;

import com.org.mfm.dto.PortfolioRequest;
import com.org.mfm.entity.Investor;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.repository.PortfolioRepository;
import com.org.mfm.service.PortfolioService;

@Service
public class PortfolioServiceImpl implements PortfolioService {

	private PortfolioRepository portfolioRepo;
	// private UserService userService;

	public PortfolioServiceImpl(PortfolioRepository portfolioRepo// , UserService userService
	) {
		this.portfolioRepo = portfolioRepo;
		// this.userService = userService;

	}

	@Override
	public PortFolio createPortfolio(PortfolioRequest folioRequest) {
		// this.userService.getUserByUserName(folioRequest.userName());

		Investor investor = new Investor();
		investor.setUserName(folioRequest.userName());

		PortFolio portFolio = new PortFolio();
		portFolio.setTittle(folioRequest.tittle());
		portFolio.setTotalGain(0);
		portFolio.setInvestor(investor);
		return this.portfolioRepo.save(portFolio);
	}

	@Override
	public void deletePortFolio(int folioNumber) {
		PortFolio folio = getPortFolio(folioNumber);
		this.portfolioRepo.delete(folio);

	}

	@Override
	public PortFolio getPortFolio(int folioNumber) {
		return this.portfolioRepo.findById(folioNumber).orElseThrow(() -> new RuntimeException("Portfolio not found"));
	}

}
