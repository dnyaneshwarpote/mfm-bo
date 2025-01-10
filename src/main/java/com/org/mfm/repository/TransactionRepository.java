package com.org.mfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findAllByFolioNumber(int portfolio);

	Optional<Transaction> findAllByTxnId(int stockTxn);

	List<Transaction> findAllByInvestmentTypeAndFolioNumber(InvestmentType investmentType, int folioNumber);

}
