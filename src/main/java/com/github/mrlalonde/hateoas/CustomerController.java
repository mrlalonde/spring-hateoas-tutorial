package com.github.mrlalonde.hateoas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = {"application/hal+json"})
    public CollectionModel<EntityModel<Customer>> getAllCustomers() {
        var customerRepresentations = customerService.allCustomers().stream()
                .map(this::toCustomerRepresentation)
                .collect(Collectors.toList());

        Link link = linkTo(CustomerController.class).withSelfRel();
        return new CollectionModel<>(customerRepresentations, link);
    }

    @GetMapping("/{customerId}")
    public EntityModel<Customer> getCustomerById(@PathVariable String customerId) {
        return toCustomerRepresentation(customerService.getCustomerDetail(customerId));
    }

    @GetMapping(value = "/{customerId}/orders", produces = {"application/hal+json"})
    public CollectionModel<EntityModel<Order>> getOrdersForCustomer(@PathVariable final String customerId) {
        Collection<EntityModel<Order>> orders = customerService.getAllOrdersForCustomer(customerId)
                .stream()
                .map(withSelfLink(customerId))
                .collect(Collectors.toList());

        return new CollectionModel<>(orders,
                linkTo(methodOn(CustomerController.class).getOrdersForCustomer(customerId)).withSelfRel());
    }

    private EntityModel<Customer> toCustomerRepresentation(Customer customer) {
        var res = new EntityModel<>(customer,
                linkTo(CustomerController.class).slash(customer.getCustomerId()).withSelfRel());

        if (customerService.getAllOrdersForCustomer(customer.getCustomerId()).size() > 0) {
            Link ordersLink = linkTo(methodOn(CustomerController.class)
                    .getOrdersForCustomer(customer.getCustomerId())).withRel("allOrders");
            res.add(ordersLink);
        }

        return res;
    }

    private Function<Order, EntityModel<Order>> withSelfLink(String customerId) {
        return order -> new EntityModel<>(order, linkTo(methodOn(CustomerController.class)
                .getOrderById(customerId, order.getOrderId()))
                .withSelfRel()
                .expand(Map.of("customerId", customerId,
                        "orderId", order.getOrderId())));

    }

    @GetMapping(value = "/{customerId}/orders/{orderId}", produces = {"application/hal+json"})
    public EntityModel<Order> getOrderById(String customerId, String orderId) {
        return customerService.getAllOrdersForCustomer(customerId).stream()
                .filter(order -> orderId.equals(order.getOrderId()))
                .map(withSelfLink(customerId))
                .findFirst()
                .orElse(null);
    }
}
