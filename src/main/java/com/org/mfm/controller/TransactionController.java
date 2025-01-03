
package com.org.mfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm.dto.TransactionRequest;
import com.org.mfm.dto.TransactionResponse;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController

@RequestMapping("/txn")
@Tag(name = "Transaction", description = "Transaction Controller for Mutual Fund, Stock, PPF, FD and RD")
public class TransactionController {

	private TransactionService txnService;

	public TransactionController(TransactionService txnService) {
		this.txnService = txnService;
	}

	@PostMapping("/add")
	public void addTransaction(TransactionRequest txnRequest) {
		Transaction txn = getType(txnRequest);
		txn.setTxnId(0);
		this.txnService.saveTransaction(txn);

	}

	@PutMapping("/update")
	public void updateTransaction(TransactionRequest txnRequest) {
		Transaction txn = getType(txnRequest);
		this.txnService.saveTransaction(txn);

	}

	@GetMapping("/get")
	public TransactionResponse getTransaction(int txnId) {
		Transaction txn = this.txnService.findTransactionByTxnId(txnId);
		String fundName = "";
		String institutionName = "";
		float quantity = 12;
		float nav = 43;
		InvestmentType invType = InvestmentType.PPF;
		return new TransactionResponse(fundName, institutionName, quantity, nav, txn.getTxnId(), txn.getTxnDate(),
				txn.getTxnAmount(), invType, txn.getTxnType(), txn.getFolioNumber());

	}

	@GetMapping("/get-all")
	public List<TransactionResponse> getTransactions(int folioNumber) {

		List<Transaction> txnList = this.txnService.findAllTxnsByFolioNumber(folioNumber);
		Optional<Transaction> txnOptional = txnList.stream().findFirst();
		Transaction txn = txnOptional.get();
		String fundName = "";
		String institutionName = "";
		float quantity = 12;
		float nav = 43;
		InvestmentType invType = InvestmentType.PPF;
		TransactionResponse txnResponse = new TransactionResponse(fundName, institutionName, quantity, nav,
				txn.getTxnId(), txn.getTxnDate(), txn.getTxnAmount(), invType, txn.getTxnType(), txn.getFolioNumber());

		List<TransactionResponse> list = new ArrayList<>();

		list.add(txnResponse);
		return list;

	}

	@DeleteMapping("/delete")
	public void deleteTransaction(TransactionRequest txnRequest) {
		this.txnService.deleteTransaction(txnRequest.txnId());

	}

	private Transaction getType(TransactionRequest txnRequest) {
		Transaction txn = null;
		if (InvestmentType.PPF == txnRequest.invType()) {
			txn = new PPFTransaction();
			txn.setFolioNumber(txnRequest.folioNumber());
			txn.setTxnAmount(txnRequest.txnAmount());
			txn.setTxnDate(txnRequest.txnDate());
			txn.setTxnType(txnRequest.txnType());
		} else {
			txn = new StockTransaction();
		}
		return txn;
	}

}
