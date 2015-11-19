package garbagetown.domain.service.customer;

import garbagetown.domain.model.Customer;

/**
 * Created by garbagetown on 10/10/15.
 */
public interface CustomerService {
    Customer findOne(String customerCode);
    Customer register(Customer customer, String rawPassword);
}
