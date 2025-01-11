package com.org.mfm.dto.mapper;

import org.springframework.stereotype.Service;

import com.org.mfm.dto.TransactionDto;
import com.org.mfm.dto.TransactionDto.TransactionDtoBuilder;
import com.org.mfm.entity.FDTransaction;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

@Service
public class TransactionDtoMapper {

	public TransactionDto toDto(Transaction txn) {
		//To-do refactor builder setter resusable
		TransactionDtoBuilder builder = TransactionDto.builder();
		builder.txnId(txn.getTxnId()).txnDate(txn.getTxnDate()).txnAmount(txn.getTxnAmount())
				.invType(txn.getInvestmentType()).txnType(txn.getTxnType());
		if (txn.getInvestmentType().equals(InvestmentType.PPF)) {
			PPFTransaction savedTxn = (PPFTransaction) txn;
			return builder.institutionName(savedTxn.getInstitutionName()).rateOfInt(savedTxn.getRateOfInt()).build();
		} else if (txn.getInvestmentType().equals(InvestmentType.STOCK)) {
			StockTransaction savedTxn = (StockTransaction) txn;
			return builder.stockName(savedTxn.getStockName()).quantity(savedTxn.getQuantity()).rate(savedTxn.getRate())
					.brokerage(savedTxn.getBrokerage()).build();
		} else if (txn.getInvestmentType().equals(InvestmentType.FD)) {
			FDTransaction savedTxn = (FDTransaction) txn;
			return builder.institutionName(savedTxn.getInstitutionName()).rateOfInt(savedTxn.getRateOfInt())
					.maturityDate(savedTxn.getMaturityDate()).build();
		}
		return builder.build();

	}

	public Transaction toEntity(TransactionDto txnDto) {
		if (txnDto.invType().equals(InvestmentType.PPF)) {

			PPFTransaction ppfTxn = new PPFTransaction();
			ppfTxn.setFolioNumber(txnDto.folioNumber());
			ppfTxn.setTxnId(txnDto.txnId());
			ppfTxn.setTxnDate(txnDto.txnDate());
			ppfTxn.setTxnAmount(txnDto.txnAmount());
			ppfTxn.setInvestmentType(txnDto.invType());
			ppfTxn.setTxnType(txnDto.txnType());
			ppfTxn.setInstitutionName(txnDto.institutionName());
			ppfTxn.setRateOfInt(txnDto.rateOfInt());
			return ppfTxn;
		} else if (txnDto.invType().equals(InvestmentType.FD)) {

			FDTransaction ppfTxn = new FDTransaction();
			ppfTxn.setFolioNumber(txnDto.folioNumber());
			ppfTxn.setTxnId(txnDto.txnId());
			ppfTxn.setTxnDate(txnDto.txnDate());
			ppfTxn.setTxnAmount(txnDto.txnAmount());
			ppfTxn.setInvestmentType(txnDto.invType());
			ppfTxn.setTxnType(txnDto.txnType());
			ppfTxn.setInstitutionName(txnDto.institutionName());
			ppfTxn.setRateOfInt(txnDto.rateOfInt());
			ppfTxn.setMaturityDate(txnDto.maturityDate());
			return ppfTxn;
		} else if (txnDto.invType().equals(InvestmentType.STOCK)) {
			StockTransaction stockTxn = new StockTransaction();
			stockTxn.setFolioNumber(txnDto.folioNumber());
			stockTxn.setTxnId(txnDto.txnId());
			stockTxn.setTxnDate(txnDto.txnDate());
			stockTxn.setTxnAmount(txnDto.txnAmount());
			stockTxn.setInvestmentType(txnDto.invType());
			stockTxn.setTxnType(txnDto.txnType());
			stockTxn.setStockName(txnDto.stockName());
			stockTxn.setBrokerage(txnDto.brokerage());
			stockTxn.setQuantity(txnDto.quantity());
			stockTxn.setRate(txnDto.rate());
			return stockTxn;
		}
		return null;

	}

}
