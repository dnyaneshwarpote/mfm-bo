package com.org.mfm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PPF;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.repository.PPFTransactionRepository;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.PPFService;
import com.org.mfm.service.PPFTransactionService;
import com.org.mfm.service.PortfolioService;

@Service
public class PPFTransactionServiceImpl implements PPFTransactionService {

	private InvestmentService investmentService;
	private PortfolioService portfolioService;
	private PPFTransactionRepository ppfTxnRepo;

	public PPFTransactionServiceImpl(PPFService ppfService, PortfolioService portfolioService,
			PPFTransactionRepository ppfTxnRepo) {
		super();
		this.investmentService = ppfService;
		this.portfolioService = portfolioService;
		this.ppfTxnRepo = ppfTxnRepo;

	}

	@Override
	public List<PPFTransaction> getTransactionsByFolioAndStockName(Integer folioNumber, String institutionName) {
		return this.ppfTxnRepo.findAllByFolioNumberAndInstitutionName(folioNumber, institutionName);
	}

	@Override
	public Transaction saveTransaction(Transaction txnRequest) {
		PPFTransaction ppfTxn = (PPFTransaction) txnRequest;
		PortFolio port = this.portfolioService.getPortFolio(txnRequest.getFolioNumber());
		Optional<Investment> investment = port.getInvestments().stream()
				.filter(inv -> InvestmentType.PPF.equals(inv.getInvestmentType())
						&& (((PPF) inv).getInstitutionName().equals(ppfTxn.getInstitutionName())))
				.findFirst();

		if (investment.isPresent()) {
			return this.investmentService.updateInvestment(ppfTxn, investment.get());
		} else {
			return this.investmentService.addInvestment(ppfTxn, port);
		}
		
	}

}
