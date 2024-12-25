package com.org.mfm.dao;

import java.util.List;

public interface InvestmentRepository<Investment> {
	public List<Investment> findAllByFolioNumber(int folioNumber);
}
