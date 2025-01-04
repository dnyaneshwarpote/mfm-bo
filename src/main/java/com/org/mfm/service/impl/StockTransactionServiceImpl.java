
package com.org.mfm.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.org.mfm.dao.StockRepository;
import com.org.mfm.dao.StockTransactionRepository;
import com.org.mfm.dao.TransactionRepository;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Stock;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.PortfolioService;
import com.org.mfm.service.StockService;
import com.org.mfm.service.StockTransactionService;

@Service
public class StockTransactionServiceImpl implements StockTransactionService {

	private InvestmentService investmentService;
	private PortfolioService portfolioService;
	private StockTransactionRepository stockTxnRepo;
	private TransactionRepository txnRepo;
	private StockRepository stockRepo;

	public StockTransactionServiceImpl(StockService stockService, PortfolioService portfolioService,
			StockTransactionRepository stockTxnRepo, TransactionRepository txnRepo, StockRepository stockRepo) {
		this.investmentService = stockService;
		this.portfolioService = portfolioService;
		this.stockTxnRepo = stockTxnRepo;
		this.txnRepo = txnRepo;
		this.stockRepo = stockRepo;

	}

	@Override
	public List<Transaction> getTransactionsByFolioAndStockName(Integer folioNumber, String stockName) {
		return null;
		// return this.stockTxnRepo.findAllByFolioNumberAndStockName(folioNumber,
		// stockName);
	}

	@Override
	public Transaction saveTransaction(Transaction txnRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
