package com.org.mfm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.repository.InvestmentRepository;
import com.org.mfm.repository.PortfolioRepository;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.StockService;

@Service
public class InvestmentServiceImpl {

	InvestmentRepository<Investment> investmentRepo;
	private PortfolioRepository portRepository;

	private final Map<InvestmentType, InvestmentService> serviceMap;

	InvestmentServiceImpl(PortfolioRepository portRepository, StockService stockService) {
		serviceMap = new HashMap<>();
		this.portRepository = portRepository;
		serviceMap.put(InvestmentType.STOCK, stockService);
	}

	public List<?> findAllByFolioNumber(int folioNumber) {
		PortFolio port = portRepository.findById(folioNumber).orElse(new PortFolio());
		return port.getInvestments();
	}

	public List<?> getInvestments(InvestmentType invType, int folioNumber) {
		InvestmentService invService = getInvestmentServiceInstance(invType);
		return invService.findAllByFolioNumber(folioNumber);
	}

	public InvestmentService getInvestmentServiceInstance(InvestmentType investmentType) {
		return serviceMap.get(investmentType);
	}

}
