package com.org.mfm.repository;

import java.util.List;

public interface InvestmentRepository<Investment> {
	public List<Investment> findAllByFolioNumber(int folioNumber);
}
