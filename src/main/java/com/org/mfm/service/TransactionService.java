package com.org.mfm.service;

import java.util.List;

import com.org.mfm.entity.Transaction;

public interface TransactionService {

	void deleteTransaction(int txnId);

	List<Transaction> findAllTxnsByFolioNumber(Integer folioNumber);

	Transaction findTransactionByTxnId(Integer integer);

	Transaction saveTransaction(Transaction txnRequest);

}
