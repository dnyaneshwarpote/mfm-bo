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

import com.org.mfm.dto.TransactionDto;
import com.org.mfm.dto.mapper.TransactionDtoMapper;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Controller for Mutual Fund, Stock, PPF, FD and RD transactions")
public class TransactionController {

	private TransactionDtoMapper txnDtoMapper;
	private TransactionService txnService;

	public TransactionController(TransactionService txnService, TransactionDtoMapper txnDtoMapper) {
		this.txnService = txnService;
		this.txnDtoMapper = txnDtoMapper;
	}

	@Operation(description = "API to addition of new transaction", summary = "Record/add new transaction", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@PostMapping("/add")
	public ResponseEntity<TransactionDto> addTransaction(TransactionDto txnRequest) {

		Transaction txn = this.txnDtoMapper.toEntity(txnRequest);
		Transaction savedTxn = this.txnService.saveTransaction(txn);
		return ResponseEntity.ok(this.txnDtoMapper.toDto(savedTxn));
	}

	@Operation(description = "API to deletion of the transaction", summary = "Delete the transaction", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@DeleteMapping("/delete")
	public void deleteTransaction(TransactionDto txnRequest) {
		this.txnService.deleteTransaction(txnRequest.txnId());
	}

	@Operation(description = "API to fetch the transaction details", summary = "Get the transaction details", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/get/{txnId}")
	public ResponseEntity<TransactionDto> getTransaction(@PathVariable("txnId") int txnId) {
		Transaction txn = this.txnService.findTransactionByTxnId(txnId);
		return ResponseEntity.ok(this.txnDtoMapper.toDto(txn));

	}

	@Operation(description = "API to fetch all transactions associated with folio number and based on Investment Type", summary = "Get all the transactions recorded under current portfolio", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@GetMapping("/get-all/{investmentType}/{folioNumber}")
	public List<TransactionDto> getTransactions(@PathVariable("investmentType") InvestmentType investmentType,
			@PathVariable("folioNumber") int folioNumber) {
		return this.txnService.getTransactions(investmentType,folioNumber, txnDtoMapper);
	}


	@Operation(description = "API to update the transaction details", summary = "Update the transaction details", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "Unauthorized / Invalid Token", responseCode = "403") })
	@PutMapping("/update")
	public void updateTransaction(TransactionDto txnRequest) {
		Transaction txn = this.txnDtoMapper.toEntity(txnRequest);
		this.txnService.saveTransaction(txn);

	}

}
