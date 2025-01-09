package com.org.mfm.enums;

import lombok.Getter;

public enum TransactionType {
	BONUS('B'), BUY('B'), DIVIDENT('D'), INTEREST('I'), MERGE('M'), SELL('S');

	@Getter
	public final char txnType;

	TransactionType(char txnType) {
		this.txnType = txnType;
	}
}
