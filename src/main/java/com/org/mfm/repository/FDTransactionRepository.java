package com.org.mfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.FDTransaction;
import com.org.mfm.entity.Transaction;

public interface FDTransactionRepository extends JpaRepository<FDTransaction, Integer> {
	List<Transaction> findAllByFolioNumber(Integer folioNumber);

	List<FDTransaction> findAllByFolioNumberAndInstitutionName(Integer folioNumber, String institutionName);

}
