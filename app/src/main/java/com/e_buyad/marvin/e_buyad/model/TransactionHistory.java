package com.e_buyad.marvin.e_buyad.model;

/**
 * User-defined model for transaction history
 */
public class TransactionHistory {
    private String transactionDate;
    private Double totalAmount;

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }
}
