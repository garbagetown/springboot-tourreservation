package garbagetown.domain.service.userdetails;

import garbagetown.domain.model.Customer;
import garbagetown.domain.repository.customer.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by garbagetown on 10/25/15.
 */
@Service
public class ReservationUserDetailsService implements UserDetailsService {

    @Inject
    CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer customer = repository.findOne(s);
        if (customer == null) {
            throw new UsernameNotFoundException(s + " is not found.");
        }
        return new ReservationUserDetails(customer);
    }
}
