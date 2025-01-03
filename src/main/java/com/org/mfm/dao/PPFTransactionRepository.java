package com.org.mfm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.Transaction;

public interface PPFTransactionRepository extends JpaRepository<PPFTransaction, Integer> {
	List<PPFTransaction> findAllByFolioNumberAndInstitutionName(Integer folioNumber, String institutionName);

	List<Transaction> findAllByFolioNumber(Integer folioNumber);

}
