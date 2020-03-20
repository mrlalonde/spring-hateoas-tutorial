package com.github.mrlalonde.hateoas;

public class Customer {
    private String customerId;
    private String customerName;
    private String companyName;

    protected Customer() {
    }

    Customer(String customerId, String customerName, String companyName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.companyName = companyName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}