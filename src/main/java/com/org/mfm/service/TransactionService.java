package com.org.mfm.service;

import java.util.List;

import com.org.mfm.bean.PPFTransactions;
import com.org.mfm.dto.TransactionDto;
import com.org.mfm.dto.mapper.TransactionDtoMapper;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

public interface TransactionService {

	void deleteTransaction(int txnId);

	List<Transaction> findAllTxnsByFolioNumber(Integer folioNumber);

	Transaction findTransactionByTxnId(Integer integer);

	Transaction saveTransaction(Transaction txnRequest);

	List<Transaction> findAllTxnsByInvestmentTypeAndFolioNumber(InvestmentType investmentType,int folioNumber);

	List<TransactionDto> getTransformedTransaction(List<PPFTransactions> list);

	List<TransactionDto> getTransactions(InvestmentType investmentType, int folioNumber, TransactionDtoMapper txnDtoMapper);

}
