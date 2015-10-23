package garbagetown.domain.service;

import garbagetown.domain.model.Customer;
import garbagetown.domain.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.sequencer.Sequencer;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Created by garbagetown on 10/10/15.
 */
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    @Inject
    CustomerRepository repository;

    @Inject
    Sequencer<String> customerCodeSeq;

    @Inject
    PasswordEncoder passwordEncoder;

    @Override
    public Customer findOne(String customerCode) {
        return repository.findOne(customerCode);
    }

    @Override
    public Customer register(Customer customer, String rawPassword) {
        String customerCode = customerCodeSeq.getNext();
        String password = passwordEncoder.encode(rawPassword);

        customer.setCustomerCode(customerCode);
        customer.setCustomerPass(password);

        return repository.save(customer);
    }
}
