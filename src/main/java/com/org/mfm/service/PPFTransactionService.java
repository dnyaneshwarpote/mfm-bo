
package com.org.mfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.Transaction;

@Service
public interface PPFTransactionService {

	List<PPFTransaction> getTransactionsByFolioAndStockName(Integer folioNumber, String stockName);

	Transaction saveTransaction(Transaction txnRequest);

}
