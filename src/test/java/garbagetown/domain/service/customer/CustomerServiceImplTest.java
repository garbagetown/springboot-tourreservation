package garbagetown.domain.service.customer;

import garbagetown.domain.model.Customer;
import garbagetown.domain.repository.customer.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.terasoluna.gfw.common.sequencer.Sequencer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by garbagetown on 11/19/15.
 */
public class CustomerServiceImplTest {

    CustomerServiceImpl customerService;
    CustomerRepository customerRepository;
    Sequencer<String> customerCodeSeq;
    BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        customerService = new CustomerServiceImpl();

        customerRepository = mock(CustomerRepository.class);
        customerService.customerRepository = customerRepository;

        customerCodeSeq = mock(Sequencer.class);
        customerService.customerCodeSeq = customerCodeSeq;

        passwordEncoder = new BCryptPasswordEncoder();
        customerService.passwordEncoder = passwordEncoder;
    }

    @Test
    public void testFindOne() {
        String customerCode = "00000001";

        Customer customer = new Customer();
        when(customerRepository.findOne(customerCode)).thenReturn(customer);

        Customer result = customerService.findOne(customerCode);

        assertThat(result, is(customer));
    }

    @Test
    public void testFindOne_notFound() {
        String customerCode = "99999999";

        when(customerRepository.findOne(customerCode)).thenReturn(null);

        Customer result = customerService.findOne(customerCode);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void testRegister() {

        String customerCode = "00000002";
        when(customerCodeSeq.getNext()).thenReturn(customerCode);

        String rawPassword = "password";
        String customerPass = passwordEncoder.encode(rawPassword);

        Customer customer = new Customer();
        customer.setCustomerCode(customerCode);
        customer.setCustomerPass(customerPass);

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.register(customer, rawPassword);

        assertThat(result.getCustomerCode(), is(customerCode));
        assertTrue(passwordEncoder.matches(rawPassword, result.getCustomerPass()));
    }
}
