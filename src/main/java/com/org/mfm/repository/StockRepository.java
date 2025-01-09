package com.org.mfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {

}
