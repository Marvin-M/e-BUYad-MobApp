package com.e_buyad.marvin.e_buyad.model;

/**
 * User-defined model for shopping cart
 */
public class ShoppingCart {
    private String transactionId;
    private String genericName;
    private String quantity;
    private String transactionDate;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public String getGenericName() {
        return this.genericName;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return this.quantity;
    }
}
