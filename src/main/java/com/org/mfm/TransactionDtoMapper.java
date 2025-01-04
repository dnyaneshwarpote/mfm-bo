package com.org.mfm;

import org.springframework.stereotype.Service;

import com.org.mfm.dto.TransactionDto;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

@Service
public class TransactionDtoMapper {

	public TransactionDto toDto(Transaction txn) {

		if (txn.getInvestmentType().equals(InvestmentType.PPF)) {
			PPFTransaction savedTxn = (PPFTransaction) txn;
			return new TransactionDto(savedTxn.getFolioNumber(), savedTxn.getTxnId(), savedTxn.getTxnDate(),
					savedTxn.getTxnAmount(), savedTxn.getInvestmentType(), savedTxn.getTxnType(), null, null,
					savedTxn.getInstitutionName(), 0, 0, 0, 0);
		}
		return new TransactionDto(txn.getFolioNumber(), txn.getTxnId(), txn.getTxnDate(), txn.getTxnAmount(),
				txn.getInvestmentType(), txn.getTxnType(), null, null, null, 0, 0, 0, 0);

	}

	public Transaction toEntity(TransactionDto txnDto) {
		if (txnDto.invType().equals(InvestmentType.PPF)) {
			PPFTransaction ppf = new PPFTransaction();
			ppf.setFolioNumber(txnDto.folioNumber());
			ppf.setTxnId(txnDto.txnId());
			ppf.setTxnDate(txnDto.txnDate());
			ppf.setTxnAmount(txnDto.txnAmount());
			ppf.setInvestmentType(txnDto.invType());
			ppf.setTxnType(txnDto.txnType());
			ppf.setInstitutionName(txnDto.institutionName());
			return ppf;
		}
		return null;

	}

}
