package com.org.mfm.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.org.mfm.bean.PPFTransactions;
import com.org.mfm.dto.TransactionDto;
import com.org.mfm.entity.FD;
import com.org.mfm.entity.FDTransaction;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.enums.TransactionType;
import com.org.mfm.repository.FDTransactionRepository;
import com.org.mfm.service.FDService;
import com.org.mfm.service.FDTransactionService;
import com.org.mfm.service.InvestmentService;
import com.org.mfm.service.PortfolioService;

@Service
public class FDTransactionServiceImpl implements FDTransactionService {

	private InvestmentService investmentService;
	private PortfolioService portfolioService;
	private FDTransactionRepository fdTxnRepo;

	public FDTransactionServiceImpl(FDService fdService, PortfolioService portfolioService,
			FDTransactionRepository fdTxnRepo) {
		super();
		this.investmentService = fdService;
		this.portfolioService = portfolioService;
		this.fdTxnRepo = fdTxnRepo;

	}

	@Override
	public Transaction saveTransaction(Transaction txnRequest) {
		FDTransaction ppfTxn = (FDTransaction) txnRequest;
		PortFolio port = this.portfolioService.getPortFolio(txnRequest.getFolioNumber());
		Optional<Investment> investment = port.getInvestments().stream()
				.filter(inv -> InvestmentType.FD.equals(inv.getInvestmentType())
						&& (((FD) inv).getName().equals(ppfTxn.getName())))
				.findFirst();

		if (investment.isPresent()) {
			return this.investmentService.updateInvestment(ppfTxn, investment.get());
		} else {
			return this.investmentService.addInvestment(ppfTxn, port);
		}

	}

	@Override
	public List<TransactionDto> getDetailedFDTransactions(FDTransaction txn) {
		List<TransactionDto> updatedTxn = new ArrayList<>();
		double principal = txn.getTxnAmount();
		double annualInterestRate = txn.getRateOfInt();
		LocalDate depositDate = txn.getTxnDate().toLocalDate();
		int tenureYears = 5;
		LocalDate maturityDate = depositDate.plusYears(tenureYears);
		LocalDate currentDate = LocalDate.now();
		double currentAmount = principal;
		int year = 1;
		updatedTxn.add(TransactionDto.builder().folioNumber(txn.getFolioNumber()).txnId(txn.getTxnId())
				.txnDate(txn.getTxnDate()).txnAmount(txn.getTxnAmount()).txnType(txn.getTxnType())
				.rateOfInt(txn.getRateOfInt()).balanceAmount(txn.getTxnAmount()).maturityDate(txn.getMaturityDate())
				.build());
		// Calculate returns year by year
		while (depositDate.plusYears(year).isBefore(currentDate) && year < tenureYears) {
			year++;
			currentAmount += currentAmount * (annualInterestRate / 100);
			updatedTxn.add(TransactionDto.builder().folioNumber(txn.getFolioNumber())
					.txnDate(Date.valueOf(LocalDate.of(depositDate.getYear() + 1, Month.MARCH, 31)))
					.txnAmount(currentAmount - principal).txnType(TransactionType.INTEREST).balanceAmount(currentAmount)
					.build());
		}

		// Check if today is after maturity date
		if (currentDate.isAfter(maturityDate)) {
			currentAmount = principal * Math.pow(1 + annualInterestRate / 100, tenureYears);
		} else {
			System.out.println("\nNote: The FD is still active and has not reached maturity.");
		}
		return updatedTxn;
	}

	@Override
	public List<PPFTransactions> convertTransactionToPPFTransactions(List<Transaction> txnList) {
		return txnList.stream().map(ppfTxn -> new PPFTransactions(ppfTxn.getTxnId(), ppfTxn.getTxnDate(),
				ppfTxn.getTxnAmount(), ppfTxn.getTxnType(), 0)).toList();

	}

	@Override
	public List<FDTransaction> getTransactionsByFolioAndName(Integer folioNumber, String name) {
		return this.fdTxnRepo.findAllByFolioNumberAndName(folioNumber, name);
	}
}
