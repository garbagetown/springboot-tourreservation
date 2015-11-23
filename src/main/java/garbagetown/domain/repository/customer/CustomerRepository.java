package garbagetown.domain.repository.customer;

import garbagetown.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by garbagetown on 10/10/15.
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
