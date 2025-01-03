package com.org.mfm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.org.mfm.dao.PortfolioRepository;
import com.org.mfm.dao.StockRepository;
import com.org.mfm.dto.StockTxnRequest;
import com.org.mfm.entity.Investment;
import com.org.mfm.entity.PortFolio;
import com.org.mfm.entity.Stock;
import com.org.mfm.entity.StockTransaction;
import com.org.mfm.entity.Transaction;
import com.org.mfm.enums.InvestmentType;
import com.org.mfm.service.StockService;

import jakarta.validation.Valid;

@Service
public class StockServiceImpl implements StockService {

	private StockRepository stockRepository;

	private PortfolioRepository portRepository;

	public StockServiceImpl(StockRepository stockRepository, PortfolioRepository portRepository) {
		this.stockRepository = stockRepository;
		this.portRepository = portRepository;
	}

	@Override
	public void updateStockInvestment(StockTransaction stockTxn, Investment investment) {
		Stock stockInvestment = (Stock) investment;
		stockInvestment.setHeldQuantity(stockInvestment.getHeldQuantity() + stockTxn.getQuantity());
		stockInvestment.setInvestmentValue(stockInvestment.getInvestmentValue() + stockTxn.getTxnAmount());
		stockInvestment.getTransactions().add(stockTxn);
		stockTxn.setInvestment(stockInvestment);
		stockRepository.save(stockInvestment);
	}

	public void addStockInvestment(StockTransaction stockTxn, PortFolio port) {
		Stock stockInvestment = new Stock();
		double txnAmount = stockTxn.getTxnAmount();
		stockInvestment.setHeldQuantity(stockTxn.getQuantity());
		stockInvestment.setInvestmentValue(txnAmount);
		stockInvestment.setCurrentValue(txnAmount);
		stockInvestment.setInvestmentType(InvestmentType.STOCK);
		stockInvestment.setAveragePrice(txnAmount / stockTxn.getQuantity());
		stockInvestment.setStockName(stockTxn.getStockName());
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
	public StockTransaction mapToEntityTransaction(@Valid StockTxnRequest stockTxnRequest) {
		StockTransaction stockTxn = new StockTransaction();
		stockTxn.setFolioNumber(stockTxnRequest.folioNumber());
		stockTxn.setBrokerage(stockTxnRequest.brokerage());
		stockTxn.setQuantity(stockTxnRequest.quantity());
		stockTxn.setRate(stockTxnRequest.rate());
		stockTxn.setStockName(stockTxnRequest.stockName());
		stockTxn.setTxnAmount(stockTxnRequest.txnAmount());
		stockTxn.setTxnDate(stockTxnRequest.txnDate());
		stockTxn.setTxnType(stockTxnRequest.txnType());
		return stockTxn;
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
	public StockTransaction updateInvestment(Transaction txn, Investment investment) {
		StockTransaction stockTxn = (StockTransaction) txn;
		Stock stock = (Stock) investment;
		int heldQuantity=stock.getHeldQuantity() + stockTxn.getQuantity();
		double inValue=stock.getInvestmentValue() + stockTxn.getTxnAmount();
		stock.setHeldQuantity(heldQuantity);
		stock.setAveragePrice(inValue/heldQuantity);
		stock.setInvestmentValue(inValue);
		stock.getTransactions().add(stockTxn);
		stockTxn.setInvestment(stock);
		stockRepository.save(stock);
		return stockTxn;
	}

	@Override
	public StockTransaction addInvestment(Transaction txn, PortFolio port) {
		StockTransaction stockTxn = (StockTransaction) txn;
		double txnAmount = stockTxn.getTxnAmount();
		Stock stock = new Stock();
		stock.setHeldQuantity(stockTxn.getQuantity());
		stock.setInvestmentValue(txnAmount);
		stock.setCurrentValue(txnAmount);
		stock.setInvestmentType(InvestmentType.STOCK);
		stock.setAveragePrice(txnAmount / stockTxn.getQuantity());
		stock.setNetProfit(0);
		stock.setRealizedProfit(0);
		stock.setStockName(stockTxn.getStockName());
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(stockTxn);
		stock.setTransactions(transactions);
		stockTxn.setInvestment(stock);
		List<Investment> invLst = new ArrayList<>();
		invLst.add(stock);
		port.setInvestments(invLst);
		stock.setPorfolio(port);
		portRepository.save(port);
		return stockTxn;

	}

	@Override
	public void updateInvestmentTransaction(PortFolio port, StockTransaction txnRequest) {

		List<Investment> investments = port.getInvestments().stream()
				.filter(inv -> InvestmentType.STOCK.equals(inv.getInvestmentType())).toList();

		Stock stockInvestment = (Stock) investments.stream()
				.filter(inv -> ((Stock) inv).getStockName().equals(txnRequest.getStockName())).findFirst().get();

		StockTransaction stockTxn = (StockTransaction) stockInvestment.getTransactions().stream()
				.filter(inv -> inv.getTxnId() == txnRequest.getTxnId()).findFirst().get();

		stockTxn.setBrokerage(txnRequest.getBrokerage());
		stockTxn.setQuantity(txnRequest.getQuantity());
		stockTxn.setRate(txnRequest.getRate());
		stockTxn.setTxnAmount(txnRequest.getTxnAmount());
		stockTxn.setTxnDate(txnRequest.getTxnDate());
		stockTxn.setTxnType(txnRequest.getTxnType());

		int newHeldQuantity = (stockInvestment.getHeldQuantity() + txnRequest.getQuantity())
				- stockInvestment.getHeldQuantity();
		double investmentValue = (stockInvestment.getInvestmentValue() + txnRequest.getTxnAmount())
				- stockInvestment.getInvestmentValue();
		double currentValue = (stockInvestment.getCurrentValue() + txnRequest.getTxnAmount())
				- stockInvestment.getCurrentValue();

		stockInvestment.setHeldQuantity(newHeldQuantity);
		stockInvestment.setInvestmentValue(investmentValue);
		stockInvestment.setCurrentValue(currentValue);
		stockInvestment.setAveragePrice(investmentValue / newHeldQuantity);

		stockRepository.save(stockInvestment);

	}

}
