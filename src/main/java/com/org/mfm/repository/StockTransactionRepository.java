package com.org.mfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer> {
	List<Transaction> findAllByFolioNumber(Integer folioNumber);

	List<Transaction> findAllByFolioNumberAndStockName(Integer folioNumber, String stockName);

}
