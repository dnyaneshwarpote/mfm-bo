package com.org.mfm.dto.mapper;

import org.springframework.stereotype.Service;

import com.org.mfm.dto.TransactionDto;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

@Service
public class TransactionDtoMapper {

	public TransactionDto toDto(Transaction txn) {

		if (txn.getInvestmentType().equals(InvestmentType.PPF)) {
			PPFTransaction savedTxn = (PPFTransaction) txn;
			return new TransactionDto(savedTxn.getFolioNumber(), savedTxn.getTxnId(), savedTxn.getTxnDate(),
					savedTxn.getTxnAmount(), savedTxn.getInvestmentType(), savedTxn.getTxnType(), null, null,
					savedTxn.getInstitutionName(), 0, 0, 0, 0, savedTxn.getRateOfInt());
		} else if (txn.getInvestmentType().equals(InvestmentType.STOCK)) {
			StockTransaction savedTxn = (StockTransaction) txn;
			return TransactionDto.builder().folioNumber(savedTxn.getFolioNumber())
					.txnId(savedTxn.getTxnId())
					.txnDate(savedTxn.getTxnDate())
					.txnAmount(savedTxn.getTxnAmount())
					.invType(savedTxn.getInvestmentType())
					.txnType(savedTxn.getTxnType())
					.stockName(savedTxn.getStockName())
					.quantity(savedTxn.getQuantity())
					.rate(savedTxn.getRate())
					.brokerage(savedTxn.getBrokerage()).build();
		}
		return new TransactionDto(txn.getFolioNumber(), txn.getTxnId(), txn.getTxnDate(), txn.getTxnAmount(),
				txn.getInvestmentType(), txn.getTxnType(), null, null, null, 0, 0, 0, 0, 0);

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
