package com.org.mfm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	Optional<Transaction> findAllByTxnId(int stockTxn);

	List<Transaction> findAllByFolioNumber(int portfolio);

}
