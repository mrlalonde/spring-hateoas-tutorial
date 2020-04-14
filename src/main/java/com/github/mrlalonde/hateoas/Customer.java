package com.github.mrlalonde.hateoas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    private String customerName;
    private String companyName;

    protected Customer() {
    }

    Customer(long customerId, String customerName, String companyName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.companyName = companyName;
    }

    Customer(String customerName, String companyName) {
        this.customerName = customerName;
        this.companyName = companyName;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCompanyName() {
        return companyName;

    }
}