
package com.org.mfm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.org.mfm.entity.PPFTransaction;

@Service
public interface PPFTransactionService extends TransactionService {

	List<PPFTransaction> getTransactionsByFolioAndStockName(Integer folioNumber, String stockName);

}
