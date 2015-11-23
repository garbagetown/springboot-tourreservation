package garbagetown.domain.repository.reserve;

import garbagetown.SpringbootTourreservationApplication;
import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Reserve;
import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.reserve.ReserveRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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
    public void testFindAllByCustomer_notFound() {
        String customerCode = "99999999";

        Customer customer = new Customer();
        customer.setCustomerCode(customerCode);

        List<Reserve> reservationList = reserveRepository.findAllByCustomer(customer);

        assertThat(reservationList, is(notNullValue()));
        assertThat(reservationList.size(), is(0));
    }

    @Test
    public void testCountReservedPersonSumByTourinfo() {

        Tourinfo tourinfo = new Tourinfo();
        tourinfo.setTourCode("0000000001");

        Customer customer = new Customer();
        customer.setCustomerCode("00000001");

        Reserve reserve = new Reserve();
        reserve.setReserveNo("10000001");
        reserve.setTourinfo(tourinfo);
        reserve.setReservedDay(DateTime.now().toDate());
        reserve.setAdultCount(1);
        reserve.setChildCount(2);
        reserve.setCustomer(customer);
        reserve.setTransfer("0");
        reserve.setSumPrice(1000);
        reserve.setRemarks("test");
        updateReserve(reserve);

        reserve.setReserveNo("10000002");
        reserve.setAdultCount(3);
        reserve.setChildCount(4);
        updateReserve(reserve);

        Long count = reserveRepository.countReservedPersonSumByTourinfo(tourinfo);

        assertThat(count, is(1 + 2 + 3 + 4L));
    }

    @Test
    public void testCountReservedPersonSumByTourinfo_notFound() {

        Tourinfo tourinfo = new Tourinfo();
        tourinfo.setTourCode("9999999999");

        Long count = reserveRepository.countReservedPersonSumByTourinfo(tourinfo);

        assertThat(count, is(nullValue()));
    }

    private void updateReserve(Reserve reserve) {
        String sql = "INSERT INTO reserve(" +
                "reserve_no, tour_code, reserved_day, adult_count, child_count, customer_code, transfer, sum_price, remarks" +
                ") VALUES (" +
                ":reserveNo, :tourCode, :reservedDay, :adultCount, :childCount, :customerCode, :transfer, :sumPrice, :remarks)";

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("reserveNo", reserve.getReserveNo());
        paramMap.put("tourCode", reserve.getTourinfo().getTourCode());
        paramMap.put("reservedDay", reserve.getReservedDay());
        paramMap.put("adultCount", reserve.getAdultCount());
        paramMap.put("childCount", reserve.getChildCount());
        paramMap.put("customerCode", reserve.getCustomer().getCustomerCode());
        paramMap.put("transfer", reserve.getTransfer());
        paramMap.put("sumPrice", reserve.getSumPrice());
        paramMap.put("remarks", reserve.getRemarks());

        jdbcTemplate.update(sql, paramMap);
    }
}
