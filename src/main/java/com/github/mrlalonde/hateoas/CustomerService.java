package com.github.mrlalonde.hateoas;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Service
public class CustomerService {

    private final Map<String, Customer> customers = Map.of(
            "1", new Customer("1", "Larry", "Stooges"),
            "2", new Customer("2", "Curly", "Stooges")
    );


    private final Map<String, Collection<Order>> orders = Map.of(
      "1", List.of(new Order("order-a", 10.0, 5),
                    new Order("order-b", 50.0, 1)),
      "2", List.of(new Order("order-c", 1.99, 100))
    );

    public Customer getCustomerDetail(String customerId) {
        return customers.get(customerId);
    }

    public Collection<Customer> allCustomers() {
        return customers.values();
    }

    public Collection<Order> getAllOrdersForCustomer(String customerId) {
        return orders.get(customerId);
    }
}
