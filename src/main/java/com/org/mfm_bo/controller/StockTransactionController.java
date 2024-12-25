package com.org.mfm_bo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm_bo.entity.StockTransaction;
import com.org.mfm_bo.repository.StockTransactionRepository;

@RestController
public class StockTransactionController {

	@Autowired
	StockTransactionRepository txnRepo;
	
	public StockTransaction addStockTransaction(StockTransaction txn){
		StockTransaction stockTxn=txnRepo.save(txn);
		return stockTxn; 
	}
}
