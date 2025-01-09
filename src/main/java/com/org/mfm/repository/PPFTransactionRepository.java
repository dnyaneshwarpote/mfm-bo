package com.org.mfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.Transaction;

public interface PPFTransactionRepository extends JpaRepository<PPFTransaction, Integer> {
	List<Transaction> findAllByFolioNumber(Integer folioNumber);

	List<PPFTransaction> findAllByFolioNumberAndInstitutionName(Integer folioNumber, String institutionName);

}
