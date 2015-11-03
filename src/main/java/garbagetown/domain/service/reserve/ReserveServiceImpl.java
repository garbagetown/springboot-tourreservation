package garbagetown.domain.service.reserve;

import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Reserve;
import garbagetown.domain.repository.reserve.ReserveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by garbagetown on 10/27/15.
 */
@Slf4j
@Service
@Transactional
public class ReserveServiceImpl implements ReserveService {

    @Inject
    ReserveRepository reserveRepository;

    @Override
    public List<Reserve> findAllByCustomerCode(String customerCode) {
        Customer customer = new Customer(customerCode);
        List<Reserve> reserves = reserveRepository.findAllByCustomer(customer);
        return reserves;
    }

    @Override
    public Reserve findOne(String reserveNo) {
        return reserveRepository.findOne(reserveNo);
    }
}
