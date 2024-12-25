/*
 * package com.org.mfm.dto;
 *
 * import java.util.function.Function;
 *
 * import org.springframework.stereotype.Service;
 *
 * import com.org.mfm.entity.StockTransaction;
 *
 * @Service public class StockTransactionDtoMapper implements
 * Function<StockTransaction, StockTxnRequest> {
 *
 * @Override public StockTxnRequest apply(StockTransaction stockTxn) { return
 * new StockTxnRequest(stockTxn.getStockName(), stockTxn.getQuantity(),
 * stockTxn.getRate(), stockTxn.getBrokerage(), stockTxn.getTxnId(),
 * stockTxn.getTxnDate(), stockTxn.getTxnAmount(), //stockTxn.getTxnType(),
 * stockTxn.getFolioNumber()); }
 *
 * }
 */