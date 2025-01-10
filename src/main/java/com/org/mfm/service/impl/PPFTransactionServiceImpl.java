package com.org.mfm.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.org.mfm.bean.PPFTransactions;
import com.org.mfm.dto.TransactionDto;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PPF;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.enums.TransactionType;
import com.org.mfm.repository.PPFTransactionRepository;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.PPFService;
import com.org.mfm.service.PPFTransactionService;
import com.org.mfm.service.PortfolioService;
import com.org.mfm.util.date.DateUtils;
import com.org.mfm.util.finance.InterestRateUtil;

@Service
public class PPFTransactionServiceImpl implements PPFTransactionService {

	private InvestmentService investmentService;
	private PortfolioService portfolioService;
	private PPFTransactionRepository ppfTxnRepo;

	public PPFTransactionServiceImpl(PPFService ppfService, PortfolioService portfolioService,
			PPFTransactionRepository ppfTxnRepo) {
		super();
		this.investmentService = ppfService;
		this.portfolioService = portfolioService;
		this.ppfTxnRepo = ppfTxnRepo;

	}

	@Override
	public List<PPFTransaction> getTransactionsByFolioAndStockName(Integer folioNumber, String institutionName) {
		return this.ppfTxnRepo.findAllByFolioNumberAndInstitutionName(folioNumber, institutionName);
	}

	@Override
	public Transaction saveTransaction(Transaction txnRequest) {
		PPFTransaction ppfTxn = (PPFTransaction) txnRequest;
		PortFolio port = this.portfolioService.getPortFolio(txnRequest.getFolioNumber());
		Optional<Investment> investment = port.getInvestments().stream()
				.filter(inv -> InvestmentType.PPF.equals(inv.getInvestmentType())
						&& (((PPF) inv).getInstitutionName().equals(ppfTxn.getInstitutionName())))
				.findFirst();

		if (investment.isPresent()) {
			return this.investmentService.updateInvestment(ppfTxn, investment.get());
		} else {
			return this.investmentService.addInvestment(ppfTxn, port);
		}

	}
	


	private static final Map<String, Double> inttRates = new HashMap<>();
	static {
		inttRates.put("1990-1991", 12.0);
		inttRates.put("1991-1992", 12.0);
		inttRates.put("1992-1993", 12.0);
		inttRates.put("1993-1994", 12.0);
		inttRates.put("1994-1995", 12.0);
		inttRates.put("1995-1996", 12.0);
		inttRates.put("1996-1997", 12.0);
		inttRates.put("1997-1998", 12.0);
		inttRates.put("1998-1999", 12.0);
		inttRates.put("1999-2000", 12.0);
		inttRates.put("2000-2001", 12.0);
		inttRates.put("2001-2002", 9.5);
		inttRates.put("2002-2003", 9.5);
		inttRates.put("2003-2004", 9.0);
		inttRates.put("2004-2005", 8.0);
		inttRates.put("2005-2006", 8.0);
		inttRates.put("2006-2007", 8.0);
		inttRates.put("2007-2008", 8.0);
		inttRates.put("2008-2009", 8.0);
		inttRates.put("2009-2010", 8.0);
		inttRates.put("2010-2011", 8.0);
		inttRates.put("2011-2012", 8.0);
		inttRates.put("2012-2013", 8.8);
		inttRates.put("2013-2014", 8.71);
		inttRates.put("2014-2015", 8.7);
		inttRates.put("2015-2016", 8.7);
		inttRates.put("2016-2017", 8.7);
		inttRates.put("2017-2018", 7.85);
		inttRates.put("2018-2019", 8.0);
		inttRates.put("2019-2020", 8.0);
		inttRates.put("2020-2021", 7.1);
		inttRates.put("2021-2022", 7.1);
		inttRates.put("2022-2023", 7.1);
		inttRates.put("2023-2024", 7.1);
		inttRates.put("2024-2025", 7.1);
	}

	@Override
	public List<TransactionDto> calculateAverageByYear(List<PPFTransactions> transactions) {

		List<TransactionDto> updatedTxns = new ArrayList<>();
		int endYear = transactions.stream().mapToInt(PPFTransactions::getYear).max().orElse(0);
		int startYear = transactions.stream().mapToInt(PPFTransactions::getYear).min().orElse(0);

		Map<Integer, List<PPFTransactions>> yearlyTransactions = transactions.stream()
				.collect(Collectors.groupingBy(w -> w.getYear()));
		double balanceAmount = 0;
		// Calculate average yearly balance for each year
		for (int year = startYear; year <= endYear; year++) {
			List<PPFTransactions> txns = yearlyTransactions.getOrDefault(year, Collections.emptyList());

			// Monthly balances for the year
			double[] monthlyBalances = new double[12];
			Arrays.fill(monthlyBalances, 0.0);
			// Apply each transaction to calculate balances
			for (PPFTransactions transaction : txns) {
				int financialMonth = DateUtils.mapToFinancialYearMonth(transaction.getMonth()) - 1;
				for (int finMonth = financialMonth; finMonth < 12; finMonth++) {
					monthlyBalances[finMonth] += balanceAmount + transaction.getAmount();
				}
				balanceAmount = balanceAmount + transaction.getAmount();
				updatedTxns.add(TransactionDto.builder().txnId(transaction.getTxnId())
						.txnDate((Date) transaction.getDate()).txnAmount(transaction.getAmount())
						.txnType(transaction.getTransactionType()).balanceAmount(balanceAmount).build());
			}
			if (txns.isEmpty()) {
				for (int finMonth = 0; finMonth < 12; finMonth++) {
					monthlyBalances[finMonth] += balanceAmount;
				}
			}
			// Calculate total balance for the year
			double yearlyBalance = 0.0;
			for (double monthlyBalance : monthlyBalances) {
				yearlyBalance += monthlyBalance;
			}

			// Average yearly balance
			double averageBalance = (yearlyBalance / 12);

			Date txnDate = Date.valueOf(LocalDate.of(year + 1, Month.MARCH, 31));
			double interrest = InterestRateUtil.calculateInterest(averageBalance,
					inttRates.get(DateUtils.getFinancialYear(txnDate)));
			balanceAmount = balanceAmount + interrest;
			Date currDate = new Date(System.currentTimeMillis());

			if (currDate.after(txnDate)) {
				updatedTxns.add(TransactionDto.builder().txnDate(txnDate).txnAmount(interrest)
						.txnType(TransactionType.INTEREST).balanceAmount(balanceAmount).build());
			}

		}
		Collections.sort(updatedTxns, Comparator.comparing(TransactionDto::txnDate));
		return updatedTxns;
	}



}
