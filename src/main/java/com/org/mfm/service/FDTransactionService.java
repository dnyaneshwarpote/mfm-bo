
package com.org.mfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.org.mfm.bean.PPFTransactions;
import com.org.mfm.dto.TransactionDto;
import com.org.mfm.entity.FDTransaction;
import com.org.mfm.entity.Transaction;

@Service
public interface FDTransactionService {

	List<FDTransaction> getTransactionsByFolioAndName(Integer folioNumber, String name);

	Transaction saveTransaction(Transaction txnRequest);

	List<PPFTransactions> convertTransactionToPPFTransactions(List<Transaction> txnList);

	List<TransactionDto> getDetailedFDTransactions(FDTransaction txn);

}
