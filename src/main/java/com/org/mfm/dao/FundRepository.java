package com.org.mfm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.mfm.entity.Fund;

public interface FundRepository extends JpaRepository<Fund, Integer> {

}
