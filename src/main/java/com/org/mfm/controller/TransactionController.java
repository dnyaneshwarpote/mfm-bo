
package com.org.mfm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.mfm.TransactionDtoMapper;
import com.org.mfm.dto.TransactionDto;
import com.org.mfm.entity.Transaction;
import com.org.mfm.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController

@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Transaction Controller for Mutual Fund, Stock, PPF, FD and RD")
public class TransactionController {

	private TransactionService txnService;
	private TransactionDtoMapper txnDtoMapper;

	public TransactionController(TransactionService txnService, TransactionDtoMapper txnDtoMapper) {
		this.txnService = txnService;
		this.txnDtoMapper = txnDtoMapper;
	}

	@Operation(description = "Get endpoint for manager", summary = "This is a summary for management get endpoint", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@PostMapping("/add")
	public ResponseEntity<TransactionDto> addTransaction(TransactionDto txnRequest) {

		Transaction txn = this.txnDtoMapper.toEntity(txnRequest);

		Transaction savedTxn = this.txnService.saveTransaction(txn);

		return ResponseEntity.ok(this.txnDtoMapper.toDto(savedTxn));
	}

	@Operation(description = "Delete transaction endpoint", summary = "Delete Transaction", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@DeleteMapping("/delete")
	public void deleteTransaction(TransactionDto txnRequest) {
		this.txnService.deleteTransaction(txnRequest.txnId());

	}

	@Operation(description = "Get endpoint for Transaction", summary = "This is a summary for management get endpoint", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/get/{txnId}")
	public ResponseEntity<TransactionDto> getTransaction(@PathVariable("txnId") int txnId) {
		Transaction txn = this.txnService.findTransactionByTxnId(txnId);
		return ResponseEntity.ok(this.txnDtoMapper.toDto(txn));

	}

	@Operation(description = "Get endpoint for transactions based on portfolio", summary = "Get endpoint for transactions", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/get-all/{folioNumber}")
	public List<TransactionDto> getTransactions(@PathVariable("folioNumber") int folioNumber) {
		List<Transaction> txnList = this.txnService.findAllTxnsByFolioNumber(folioNumber);
		return txnList.stream().map(txn -> this.txnDtoMapper.toDto(txn)).toList();

	}

	@PutMapping("/update")
	public void updateTransaction(TransactionDto txnRequest) {
		Transaction txn = this.txnDtoMapper.toEntity(txnRequest);
		this.txnService.saveTransaction(txn);

	}

}
