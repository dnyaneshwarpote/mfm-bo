package com.org.mfm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.PortFolio;

public interface PortfolioRepository extends JpaRepository<PortFolio, Integer> {

}
