package com.org.mfm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
	List<Transaction> findAllByFolioNumber(Integer folioNumber);

	List<StockTransaction> findAllByFolioNumberAndStockName(Integer folioNumber, String stockName);

}
