package com.org.mfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.FundTransaction;

public interface FundTransactionRepository extends JpaRepository<FundTransaction, Integer> {

}
