package com.org.mfm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.org.mfm.dao.PPFRepository;
import com.org.mfm.dao.PPFTransactionRepository;
import com.org.mfm.dao.TransactionRepository;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PPF;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Stock;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.PPFService;
import com.org.mfm.service.PPFTransactionService;
import com.org.mfm.service.PortfolioService;

@Service
public class PPFTransactionServiceImpl implements PPFTransactionService {

	private InvestmentService investmentService;
	private PortfolioService portfolioService;
	private PPFTransactionRepository ppfTxnRepo;
	private TransactionRepository txnRepo;
	private PPFRepository ppfRepo;

	public PPFTransactionServiceImpl(PPFService ppfService, PortfolioService portfolioService,
			PPFTransactionRepository stockTxnRepo, TransactionRepository txnRepo, PPFRepository ppfRepo) {
		this.investmentService = ppfService;
		this.portfolioService = portfolioService;
		this.ppfTxnRepo = stockTxnRepo;
		this.txnRepo = txnRepo;
		this.ppfRepo = ppfRepo;

	}

	@Override
	public StockTransaction saveTransaction(Transaction txnRequest) {
		StockTransaction stockTxn = (StockTransaction) txnRequest;
		PortFolio port = this.portfolioService.getPortFolio(txnRequest.getFolioNumber());
		Optional<Investment> investment = port.getInvestments().stream()
				.filter(inv -> InvestmentType.STOCK.equals(inv.getInvestmentType())
						&& (((Stock) inv).getStockName().equals(stockTxn.getStockName())))
				.findFirst();

		if (investment.isPresent()) {
			return this.investmentService.updateInvestment(stockTxn, investment.get());
		} else {
			return this.investmentService.addInvestment(stockTxn, port);
		}

	}

	@Override
	public List<Transaction> findAllTxnsByFolioNumber(Integer folioNumber) {
		return this.txnRepo.findAllByFolioNumber(folioNumber);
	}

	@Override
	public List<PPFTransaction> getTransactionsByFolioAndStockName(Integer folioNumber, String institutionName) {
		return this.ppfTxnRepo.findAllByFolioNumberAndInstitutionName(folioNumber, institutionName);
	}

	@Override
	public Transaction findTransactionByTxnId(Integer txnId) {
		return this.txnRepo.findById(txnId).orElseThrow(() -> new RuntimeException("Stock Transaction Not found"));

	}

	@Override
	public void deleteTransaction(int txnId) {
		StockTransaction txn = (StockTransaction) findTransactionByTxnId(txnId);
		PPF ppf = (PPF) txn.getInvestment();
		ppf.setHeldQuantity(ppf.getHeldQuantity() - txn.getQuantity());
		ppfRepo.save(ppf);
		this.txnRepo.deleteById(txnId);
	}

}
