
package com.org.mfm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm.dto.TransactionRequest;
import com.org.mfm.dto.TransactionResponse;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController

@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Transaction Controller for Mutual Fund, Stock, PPF, FD and RD")
public class TransactionController {

	private TransactionService txnService;

	public TransactionController(TransactionService txnService) {
		this.txnService = txnService;
	}

	@Operation(description = "Get endpoint for manager", summary = "This is a summary for management get endpoint", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@PostMapping("/add")
	public ResponseEntity<TransactionResponse> addTransaction(TransactionRequest txnRequest) {
		Transaction txn = this.txnService.getTypeObject(txnRequest);
		Transaction savedTxn = this.txnService.saveTransaction(txn);

		return ResponseEntity.ok(getSavedTransaction(savedTxn));
	}

	private TransactionResponse getSavedTransaction(Transaction txn) {
		TransactionResponse transactionResponse = null;
		if (txn.getInvestmentType().equals(InvestmentType.PPF)) {
			PPFTransaction savedTxn = (PPFTransaction) txn;
			transactionResponse = new TransactionResponse(savedTxn.getFolioNumber(), savedTxn.getTxnId(),
					savedTxn.getTxnDate(), savedTxn.getTxnAmount(), savedTxn.getInvestmentType(), savedTxn.getTxnType(),
					null, null, savedTxn.getInstitutionName(), 0, 0, 0, 0);
		} else {
			StockTransaction savedTxn = (StockTransaction) txn;
			transactionResponse = new TransactionResponse(savedTxn.getFolioNumber(), savedTxn.getTxnId(),
					savedTxn.getTxnDate(), savedTxn.getTxnAmount(), savedTxn.getInvestmentType(), savedTxn.getTxnType(),
					null, savedTxn.getStockName(), null, savedTxn.getQuantity(), 0, savedTxn.getRate(),
					savedTxn.getBrokerage());
		}

		return transactionResponse;
	}

	@PutMapping("/update")
	public void updateTransaction(TransactionRequest txnRequest) {
		Transaction txn = this.txnService.getTypeObject(txnRequest);
		this.txnService.saveTransaction(txn);

	}

	@Operation(description = "Get endpoint for manager", summary = "This is a summary for management get endpoint", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/get/{txnId}")
	public ResponseEntity<TransactionResponse> getTransaction(@PathVariable("txnId") int txnId) {
		Transaction txn = this.txnService.findTransactionByTxnId(txnId);
		return ResponseEntity.ok(getSavedTransaction(txn));

	}

	@GetMapping("/get-all/{folioNumber}")
	public List<TransactionResponse> getTransactions(@PathVariable("folioNumber") int folioNumber) {
		List<Transaction> txnList = this.txnService.findAllTxnsByFolioNumber(folioNumber);
		return txnList.stream().map(d -> getSavedTransaction(d)).toList();

	}

	@DeleteMapping("/delete")
	public void deleteTransaction(TransactionRequest txnRequest) {
		this.txnService.deleteTransaction(txnRequest.txnId());

	}

}
