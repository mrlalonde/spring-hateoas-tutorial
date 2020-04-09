package com.github.mrlalonde.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private final Map<String, Collection<Order>> orders = Map.of(
            "1", List.of(new Order("order-a", 10.0, 5),
                    new Order("order-b", 50.0, 1)),
            "2", List.of(new Order("order-c", 1.99, 100))
    );

    public Customer getCustomerDetail(String customerId) {
        return customerRepository.findById(Long.valueOf(customerId)).orElse(null);
    }

    public Collection<Customer> allCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Collection<Order> getAllOrdersForCustomer(String customerId) {
        return orders.getOrDefault(customerId, Collections.emptyList());
    }

    public Customer addCustomer(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }
}
