package com.org.mfm_bo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm_bo.entity.StockTransaction;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

}
