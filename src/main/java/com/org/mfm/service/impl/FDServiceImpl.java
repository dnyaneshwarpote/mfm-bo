package com.org.mfm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.org.mfm.entity.FD;
import com.org.mfm.entity.FDTransaction;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.repository.FDRepository;
import com.org.mfm.repository.PortfolioRepository;
import com.org.mfm.service.FDService;

@Service
public class FDServiceImpl implements FDService {

	// dgddfd
	private static final Map<Integer, Double> historicalRates = new HashMap<>();

	static {
		// Populate with some sample data (Year -> Interest Rate)
		historicalRates.put(2015, 8.7);
		historicalRates.put(2016, 8.1);
		historicalRates.put(2017, 7.9);
		historicalRates.put(2018, 7.6);
		historicalRates.put(2019, 8.0);
		historicalRates.put(2020, 7.9);
		historicalRates.put(2021, 7.1);
		historicalRates.put(2022, 7.1);
		historicalRates.put(2023, 7.1);
		historicalRates.put(2024, 7.1);
	}

	private PortfolioRepository portRepository;

	private FDRepository fdRepository;

	public FDServiceImpl(FDRepository stockRepository, PortfolioRepository portRepository) {
		this.fdRepository = stockRepository;
		this.portRepository = portRepository;
	}

	@Override
	public FDTransaction addInvestment(Transaction txn, PortFolio port) {
		FDTransaction stockTxn = (FDTransaction) txn;
		double txnAmount = stockTxn.getTxnAmount();
		FD ppf = new FD();

		ppf.setInvestmentValue(txnAmount);
		ppf.setCurrentValue(txnAmount);
		ppf.setInvestmentType(InvestmentType.FD);
		ppf.setNetProfit(0);
		ppf.setName(stockTxn.getName());
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(stockTxn);
		ppf.setTransactions(transactions);
		stockTxn.setInvestment(ppf);
		List<Investment> invLst = new ArrayList<>();
		invLst.add(ppf);
		port.setInvestments(invLst);
		ppf.setPorfolio(port);
		portRepository.save(port);
		return stockTxn;

	}

	public void addPPFInvestment(PPFTransaction stockTxn, PortFolio port) {
		FD stockInvestment = new FD();
		double txnAmount = stockTxn.getTxnAmount();
		stockInvestment.setInvestmentValue(txnAmount);
		stockInvestment.setCurrentValue(txnAmount);
		stockInvestment.setInvestmentType(InvestmentType.STOCK);
		stockInvestment.setName(stockTxn.getName());
		List<Transaction> transactions = List.of(stockTxn);
		stockInvestment.setTransactions(transactions);
		stockTxn.setInvestment(stockInvestment);
		List<Investment> invLst = new ArrayList<>();
		invLst.add(stockInvestment);
		port.setInvestments(invLst);
		stockInvestment.setPorfolio(port);
		portRepository.save(port);
	}

	public void calculateInterest(double annualDeposit, int startYear, int endYear) {
		// double annualDeposit = scanner.nextDouble();

		// Input: Start year
		System.out.print("Enter the start year of investment: ");
		// int startYear = scanner.nextInt();

		// Input: End year
		System.out.print("Enter the end year of investment: ");
		// int endYear = scanner.nextInt();

		double totalInterest = 0.0;
		double totalAmount = 0.0;

		// Calculate interest for each year based on historical rates
		for (int year = startYear; year <= endYear; year++) {
			if (historicalRates.containsKey(year)) {
				double rate = historicalRates.get(year);
				double interest = (totalAmount + annualDeposit) * rate / 100;
				totalInterest += interest;
				totalAmount += annualDeposit + interest;

				System.out.printf("Year: %d, Rate: %.2f%%, Interest Earned: ₹%.2f, Total Amount: ₹%.2f%n", year, rate,
						interest, totalAmount);
			} else {
				System.out.printf("Year: %d - No historical rate available. Skipping.%n", year);
			}
		}

		// Output the results
		System.out.printf("Total Interest Earned: ₹%.2f%n", totalInterest);
		System.out.printf("Final Amount (Principal + Interest): ₹%.2f%n", totalAmount);

	}

	@Override
	public List<?> findAllByFolioNumber(int folioNumber) {
		PortFolio port = portRepository.findById(folioNumber).orElse(new PortFolio());
		return port.getInvestments();
	}

	@Override
	public List<?> getInvestments(InvestmentType stock, Integer folioNumber) {
		return Collections.emptyList();
	}

	@Override
	public PPFTransaction updateInvestment(Transaction txn, Investment investment) {
		PPFTransaction stockTxn = (PPFTransaction) txn;
		FD stock = (FD) investment;
		double inValue = stock.getInvestmentValue() + stockTxn.getTxnAmount();
		stock.setInvestmentValue(inValue);
		stock.getTransactions().add(stockTxn);
		stockTxn.setInvestment(stock);
		fdRepository.save(stock);
		return stockTxn;
	}

	@Override
	public void updateInvestmentTransaction(PortFolio port, Transaction txnReq) {
		PPFTransaction txnRequest = (PPFTransaction) txnReq;

		List<Investment> investments = port.getInvestments().stream()
				.filter(inv -> InvestmentType.STOCK.equals(inv.getInvestmentType())).toList();

		FD stockInvestment = (FD) investments.stream().filter(inv -> ((FD) inv).getName().equals(txnRequest.getName()))
				.findFirst().get();

		PPFTransaction stockTxn = (PPFTransaction) stockInvestment.getTransactions().stream()
				.filter(inv -> inv.getTxnId() == txnRequest.getTxnId()).findFirst().get();

		stockTxn.setTxnAmount(txnRequest.getTxnAmount());
		stockTxn.setTxnDate(txnRequest.getTxnDate());
		stockTxn.setTxnType(txnRequest.getTxnType());

		double investmentValue = (stockInvestment.getInvestmentValue() + txnRequest.getTxnAmount())
				- stockInvestment.getInvestmentValue();
		double currentValue = (stockInvestment.getCurrentValue() + txnRequest.getTxnAmount())
				- stockInvestment.getCurrentValue();

		stockInvestment.setInvestmentValue(investmentValue);
		stockInvestment.setCurrentValue(currentValue);

		fdRepository.save(stockInvestment);

	}

	@Override
	public void updateFDInvestment(FDTransaction ppfTxn, Investment investment) {
		FD ppfInvestment = (FD) investment;
		ppfInvestment.setInvestmentValue(ppfInvestment.getInvestmentValue() + ppfTxn.getTxnAmount());
		ppfInvestment.getTransactions().add(ppfTxn);
		ppfTxn.setInvestment(ppfInvestment);
		fdRepository.save(ppfInvestment);
	}

}
