package com.e_buyad.marvin.e_buyad.model;

/**
 * User-defined model for product
 */
public class Product {
    private String productCode;
    private String brand;
    private String generic;
    private String medName;
    private String medSize;
    private String price;

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }

    public String getGeneric() {
        return this.generic;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedName() {
        return this.medName;
    }

    public void setMedSize(String medSize) {
        this.medSize = medSize;
    }

    public String getMedSize() {
        return this.medSize;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return this.price;
    }
}
