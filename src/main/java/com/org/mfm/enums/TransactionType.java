package com.org.mfm.enums;

import lombok.Getter;

public enum TransactionType {
	BUY('B'), SELL('S'), MERGE('M'), DIVIDENT('D'), BONUS('B');

	@Getter
	public final char txnType;

	TransactionType(char txnType) {
		this.txnType = txnType;
	}
}
