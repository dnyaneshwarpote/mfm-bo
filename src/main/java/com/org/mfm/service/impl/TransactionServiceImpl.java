package com.org.mfm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.org.mfm.bean.PPFTransactions;
import com.org.mfm.dto.TransactionDto;
import com.org.mfm.dto.mapper.TransactionDtoMapper;
import com.org.mfm.entity.PPF;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.repository.PPFRepository;
import com.org.mfm.repository.TransactionRepository;
import com.org.mfm.service.PPFTransactionService;
import com.org.mfm.service.StockTransactionService;
import com.org.mfm.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private PPFRepository ppfRepo;
	private PPFTransactionService ppfTxnService;

	private StockTransactionService stockTxnService;
	private TransactionRepository txnRepo;

	public TransactionServiceImpl(StockTransactionService stockTxnService, PPFTransactionService ppfTxnService,
			TransactionRepository txnRepo, PPFRepository ppfRepo) {
		this.stockTxnService = stockTxnService;
		this.ppfTxnService = ppfTxnService;
		this.txnRepo = txnRepo;
		this.ppfRepo = ppfRepo;

	}

	@Override
	public void deleteTransaction(int txnId) {
		PPFTransaction txn = (PPFTransaction) findTransactionByTxnId(txnId);
		PPF ppf = (PPF) txn.getInvestment();
		ppfRepo.save(ppf);
		this.txnRepo.deleteById(txnId);
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
	public List<Transaction> findAllTxnsByInvestmentTypeAndFolioNumber(InvestmentType investmentType, int folioNumber) {
		return this.txnRepo.findAllByInvestmentTypeAndFolioNumber(investmentType, folioNumber);
	}

	@Override
	public List<TransactionDto> getTransformedTransaction(List<PPFTransactions> list) {
		return ppfTxnService.calculateAverageByYear(list);
	}

	@Override
	public List<TransactionDto> getTransactions(InvestmentType invType, int folioNumber,
			TransactionDtoMapper txnDtoMapper) {
		List<Transaction> txnList = findAllTxnsByInvestmentTypeAndFolioNumber(invType, folioNumber);
		if (invType.equals(InvestmentType.PPF)) {
			List<PPFTransactions> list = this.ppfTxnService.convertTransactionToPPFTransactions(txnList);
			return getTransformedTransaction(list);
		}
		return txnList.stream().map(txnDtoMapper::toDto).toList();
	}

}
