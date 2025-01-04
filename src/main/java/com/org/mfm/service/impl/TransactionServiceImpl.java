package com.org.mfm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.org.mfm.dao.PPFRepository;
import com.org.mfm.dao.TransactionRepository;
import com.org.mfm.dto.TransactionRequest;
import com.org.mfm.entity.PPF;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.PPFTransactionService;
import com.org.mfm.service.PortfolioService;
import com.org.mfm.service.StockTransactionService;
import com.org.mfm.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private StockTransactionService stockTxnService;
	private PPFTransactionService ppfTxnService;

	private TransactionRepository txnRepo;
	private PPFRepository ppfRepo;

	public TransactionServiceImpl(StockTransactionService stockTxnService, PortfolioService portfolioService,
			PPFTransactionService ppfTxnService, TransactionRepository txnRepo, PPFRepository ppfRepo) {
		this.stockTxnService = stockTxnService;
		this.ppfTxnService = ppfTxnService;
		this.txnRepo = txnRepo;
		this.ppfRepo = ppfRepo;

	}

	@Override
	public Transaction saveTransaction(Transaction txnRequest) {
		Transaction txn = null;
		if (txnRequest.getInvestmentType().equals(InvestmentType.PPF)) {
			txn = ppfTxnService.saveTransaction(txnRequest);
		} else {
			txn = stockTxnService.saveTransaction(txnRequest);
		}
		txn.setInvestment(null);
		return txn;

	}

	@Override
	public List<Transaction> findAllTxnsByFolioNumber(Integer folioNumber) {
		return this.txnRepo.findAllByFolioNumber(folioNumber);
	}

	@Override
	public Transaction findTransactionByTxnId(Integer txnId) {
		return this.txnRepo.findById(txnId).orElseThrow(() -> new RuntimeException("Stock Transaction Not found"));

	}

	@Override
	public void deleteTransaction(int txnId) {
		PPFTransaction txn = (PPFTransaction) findTransactionByTxnId(txnId);
		PPF ppf = (PPF) txn.getInvestment();
		ppfRepo.save(ppf);
		this.txnRepo.deleteById(txnId);
	}

	public Transaction getTypeObject(TransactionRequest txnRequest) {
		Transaction txn = null;
		if (InvestmentType.PPF == txnRequest.invType()) {
			PPFTransaction ppfTxn = new PPFTransaction();
			ppfTxn.setFolioNumber(txnRequest.folioNumber());
			ppfTxn.setTxnAmount(txnRequest.txnAmount());
			ppfTxn.setInstitutionName(txnRequest.institutionName());
			ppfTxn.setTxnDate(txnRequest.txnDate());
			ppfTxn.setTxnType(txnRequest.txnType());
			txn = ppfTxn;
		} else {
			txn = new StockTransaction();
		}

		txn.setTxnId(0);
		txn.setInvestmentType(txnRequest.invType());
		return txn;
	}
}