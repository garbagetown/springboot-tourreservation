package garbagetown.domain.repository;

import garbagetown.SpringbootTourreservationApplication;
import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Reserve;
import garbagetown.domain.repository.reserve.ReserveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by garbagetown on 10/27/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringbootTourreservationApplication.class)
@WebIntegrationTest
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ReserveRepositoryImplTest {

    @Inject
    NamedParameterJdbcTemplate jdbcTemplate;

    @Inject
    ReserveRepository reserveRepository;

    @Test
    public void testFindByCustomer02() {
        String customerCode = "xxxxxxxx";

        Customer customer = new Customer();
        customer.setCustomerCode(customerCode);

        List<Reserve> reservationList = reserveRepository.findAllByCustomer(customer);

        assertThat(reservationList, is(notNullValue()));
        assertThat(reservationList.size(), is(0));
    }
}
