
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
import com.org.mfm.repository.StockTransactionRepository;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.PortfolioService;
import com.org.mfm.service.StockService;
import com.org.mfm.service.StockTransactionService;

@Service
public class StockTransactionServiceImpl implements StockTransactionService {

	private InvestmentService investmentService;
	private PortfolioService portfolioService;
	private StockTransactionRepository stockTxnRepo;

	public StockTransactionServiceImpl(StockService stockService, PortfolioService portfolioService,
			StockTransactionRepository stockTxnRepo) {
		super();
		this.investmentService = stockService;
		this.portfolioService = portfolioService;
		this.stockTxnRepo = stockTxnRepo;

	}

	@Override
	public List<Transaction> getTransactionsByFolioAndStockName(Integer folioNumber, String institutionName) {
		return this.stockTxnRepo.findAllByFolioNumberAndStockName(folioNumber, institutionName);
	}

	@Override
	public Transaction saveTransaction(Transaction txnRequest) {
		PPFTransaction ppfTxn = (PPFTransaction) txnRequest;
		PortFolio port = this.portfolioService.getPortFolio(txnRequest.getFolioNumber());
		Optional<Investment> investment = port.getInvestments().stream()
				.filter(inv -> InvestmentType.STOCK.equals(inv.getInvestmentType())
						&& (((PPF) inv).getInstitutionName().equals(ppfTxn.getInstitutionName())))
				.findFirst();

		if (investment.isPresent()) {
			return this.investmentService.updateInvestment(ppfTxn, investment.get());
		} else {
			return this.investmentService.addInvestment(ppfTxn, port);
		}
	}
}
