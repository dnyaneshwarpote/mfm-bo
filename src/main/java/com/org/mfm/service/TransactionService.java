package com.org.mfm.service;

import java.util.List;

import com.org.mfm.entity.Transaction;

public interface TransactionService {

	Transaction saveTransaction(Transaction txnRequest);

	Transaction findTransactionByTxnId(Integer integer);

	List<Transaction> findAllTxnsByFolioNumber(Integer folioNumber);

	void deleteTransaction(int txnId);

}
