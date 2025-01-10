package com.org.mfm.bean;

import java.util.Calendar;
import java.util.Date;

import com.org.mfm.enums.TransactionType;

public class PPFTransactions {
	int txnId;
	int month;
	int year;
	double amount;
	Date date;
	double balanceAmount;
	double interest;
	TransactionType transactionType;

	public PPFTransactions(int txnId,Date date, double amount, TransactionType transactionType, double balanceAmount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		this.txnId=txnId;
		this.month = calendar.get(Calendar.MONTH) + 1;
		this.year = calendar.get(Calendar.YEAR);
		this.date = date;
		this.amount = amount;
		this.transactionType = transactionType;
		this.balanceAmount = balanceAmount;
	}

	public int getTxnId() {
		return txnId;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "Date : " + date + ", Transaction Amount: " + amount + ", Transaction Type : " + transactionType
				+ ", Balance Amount:" + balanceAmount;
	}

}