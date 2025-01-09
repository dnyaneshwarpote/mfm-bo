package com.org.mfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.Investor;

public interface InvestorRepository extends JpaRepository<Investor, Integer> {

	Investor findByUserName(String userName);

}