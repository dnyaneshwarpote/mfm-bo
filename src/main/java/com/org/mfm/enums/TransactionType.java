package com.org.mfm.enums;

import lombok.Getter;

public enum TransactionType {
	BUY('B'), SELL('S'), MERGE('M'), DIVIDENT('D'), BONUS('B');

	TransactionType(char txnType) {
		this.txnType = txnType;
	}

	@Getter
	public final char txnType;
}
