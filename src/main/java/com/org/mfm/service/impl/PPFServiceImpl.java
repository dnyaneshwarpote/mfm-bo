package com.org.mfm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.org.mfm.dao.PPFRepository;
import com.org.mfm.dao.PortfolioRepository;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PPF;
import com.org.mfm.entity.PPFTransaction;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.PPFService;

@Service
public class PPFServiceImpl implements PPFService {

	private PPFRepository ppfRepository;

	private PortfolioRepository portRepository;

	public PPFServiceImpl(PPFRepository stockRepository, PortfolioRepository portRepository) {
		this.ppfRepository = stockRepository;
		this.portRepository = portRepository;
	}

	@Override
	public PPFTransaction addInvestment(Transaction txn, PortFolio port) {
		PPFTransaction stockTxn = (PPFTransaction) txn;
		double txnAmount = stockTxn.getTxnAmount();
		PPF ppf = new PPF();
		ppf.setInvestmentValue(txnAmount);
		ppf.setCurrentValue(txnAmount);
		ppf.setInvestmentType(InvestmentType.PPF);
		ppf.setNetProfit(0);
		ppf.setInstitutionName(stockTxn.getInstitutionName());
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
		PPF stockInvestment = new PPF();
		double txnAmount = stockTxn.getTxnAmount();
		stockInvestment.setInvestmentValue(txnAmount);
		stockInvestment.setCurrentValue(txnAmount);
		stockInvestment.setInvestmentType(InvestmentType.STOCK);
		stockInvestment.setInstitutionName(stockTxn.getInstitutionName());
		List<Transaction> transactions = List.of(stockTxn);
		stockInvestment.setTransactions(transactions);
		stockTxn.setInvestment(stockInvestment);
		List<Investment> invLst = new ArrayList<>();
		invLst.add(stockInvestment);
		port.setInvestments(invLst);
		stockInvestment.setPorfolio(port);
		portRepository.save(port);
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
		PPF stock = (PPF) investment;
		double inValue = stock.getInvestmentValue() + stockTxn.getTxnAmount();
		stock.setInvestmentValue(inValue);
		stock.getTransactions().add(stockTxn);
		stockTxn.setInvestment(stock);
		ppfRepository.save(stock);
		return stockTxn;
	}

	@Override
	public void updateInvestmentTransaction(PortFolio port, Transaction txnReq) {
		PPFTransaction txnRequest = (PPFTransaction) txnReq;

		List<Investment> investments = port.getInvestments().stream()
				.filter(inv -> InvestmentType.STOCK.equals(inv.getInvestmentType())).toList();

		PPF stockInvestment = (PPF) investments.stream()
				.filter(inv -> ((PPF) inv).getInstitutionName().equals(txnRequest.getInstitutionName())).findFirst()
				.get();

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

		ppfRepository.save(stockInvestment);

	}

	@Override
	public void updatePPFInvestment(PPFTransaction stockTxn, Investment investment) {
		PPF ppfInvestment = (PPF) investment;
		ppfInvestment.setInvestmentValue(ppfInvestment.getInvestmentValue() + stockTxn.getTxnAmount());
		ppfInvestment.getTransactions().add(stockTxn);
		stockTxn.setInvestment(ppfInvestment);
		ppfRepository.save(ppfInvestment);
	}

}
