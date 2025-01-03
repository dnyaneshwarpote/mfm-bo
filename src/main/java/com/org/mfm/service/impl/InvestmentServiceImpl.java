package com.org.mfm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.org.mfm.dao.InvestmentRepository;
import com.org.mfm.dao.PortfolioRepository;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.StockService;

@Service
public class InvestmentServiceImpl {

	private final Map<InvestmentType, InvestmentService> serviceMap;
	InvestmentRepository<Investment> investmentRepo;

	private PortfolioRepository portRepository;

	InvestmentServiceImpl(PortfolioRepository portRepository, StockService stockService) {
		serviceMap = new HashMap<>();
		this.portRepository = portRepository;
		serviceMap.put(InvestmentType.STOCK, stockService);
	}

	public InvestmentService getInvestmentServiceInstance(InvestmentType investmentType) {
		return serviceMap.get(investmentType);
	}

	public List<?> getInvestments(InvestmentType invType, int folioNumber) {
		InvestmentService invService = getInvestmentServiceInstance(invType);
		return invService.findAllByFolioNumber(folioNumber);
	}

	public List<?> findAllByFolioNumber(int folioNumber) {
		PortFolio port = portRepository.findById(folioNumber).orElse(new PortFolio());
		return port.getInvestments();
	}

}
