package garbagetown.domain.repository.reserve;

import garbagetown.SpringbootTourreservationApplication;
import garbagetown.domain.model.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
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
    public void testFindAllByCustomer() {

        Departure departure = new Departure();
        departure.setDepCode("91");
        departure.setDepName("北海道");
        updateDeparture(departure);

        Arrival arrival = new Arrival();
        arrival.setArrCode("91");
        arrival.setArrName("北海道");
        updateArrival(arrival);

        Accommodation accommodation = new Accommodation();
        accommodation.setAccomCode("1001");
        accommodation.setAccomName("TEST");
        accommodation.setAccomTel("012-3456-7890");
        updateAccommodation(accommodation);

        Tourinfo tourinfo = new Tourinfo();
        tourinfo.setTourCode("1000000001");
        tourinfo.setPlannedDay(DateTime.now().minusMonths(1).toDate());
        tourinfo.setPlanNo("100");
        tourinfo.setTourName("tourName");
        tourinfo.setTourDays(1);
        tourinfo.setDepDay(DateTime.now().plusMonths(1).toDate());
        tourinfo.setAvaRecMax(1000);
        tourinfo.setDeparture(departure);
        tourinfo.setArrival(arrival);
        tourinfo.setAccommodation(accommodation);
        tourinfo.setBasePrice(10000);
        tourinfo.setConductor("0");
        updateTourinfo(tourinfo);

        Tourinfo tourinfo2 = new Tourinfo();
        tourinfo2.setTourCode("1000000002");
        tourinfo2.setPlannedDay(DateTime.now().minusMonths(1).toDate());
        tourinfo2.setPlanNo("100");
        tourinfo2.setTourName("tourName");
        tourinfo2.setTourDays(1);
        tourinfo2.setDepDay(DateTime.now().plusMonths(1).minusDays(1).toDate());
        tourinfo2.setAvaRecMax(1000);
        tourinfo2.setDeparture(departure);
        tourinfo2.setArrival(arrival);
        tourinfo2.setAccommodation(accommodation);
        tourinfo2.setBasePrice(10000);
        tourinfo2.setConductor("0");
        updateTourinfo(tourinfo2);

        Customer customer = new Customer();
        customer.setCustomerCode("10000001");
        customer.setCustomerName("test_name");
        customer.setCustomerKana("test_kana");
        customer.setCustomerPass("test_pass");
        customer.setCustomerBirth(new DateTime(1999, 12, 31, 0, 0).toDate());
        customer.setCustomerJob("test_job");
        customer.setCustomerTel("03-1234-5678");
        customer.setCustomerPost("123-4567");
        customer.setCustomerAdd("test_add");
        updateCustomer(customer);

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
        reserve.setTourinfo(tourinfo);
        updateReserve(reserve);

        reserve.setReserveNo("10000003");
        reserve.setTourinfo(tourinfo2);
        updateReserve(reserve);

        List<Reserve> reservationList = reserveRepository.findAllByCustomer(customer);

        assertThat(reservationList.size(), is(3));
        assertThat(reservationList.get(0).getReserveNo(), is("10000003"));
        assertThat(reservationList.get(1).getReserveNo(), is("10000001"));
        assertThat(reservationList.get(2).getReserveNo(), is("10000002"));
    }

    @Test
    public void testFindAllByCustomer_notFound() {

        Customer customer = new Customer();
        customer.setCustomerCode("99999999");

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

    private void updateDeparture(Departure departure) {
        String sql = "INSERT INTO departure (" +
                "dep_code, dep_name" +
                ") VALUES (" +
                ":depCode, :depName)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("depCode", departure.getDepCode());
        paramMap.put("depName", departure.getDepName());

        jdbcTemplate.update(sql, paramMap);
    }

    private void updateArrival(Arrival arrival) {
        String sql = "INSERT INTO arrival (" +
                "arr_code, arr_name" +
                ") VALUES (" +
                ":arrCode, :arrName)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("arrCode", arrival.getArrCode());
        paramMap.put("arrName", arrival.getArrName());

        jdbcTemplate.update(sql, paramMap);
    }

    private void updateAccommodation(Accommodation accommodation) {
        String sql = "INSERT INTO accommodation (" +
                "accom_code, accom_name, accom_tel" +
                ") VALUES (" +
                ":accomCode, :accomName, :accomTel)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("accomCode", accommodation.getAccomCode());
        paramMap.put("accomName", accommodation.getAccomName());
        paramMap.put("accomTel", accommodation.getAccomTel());

        jdbcTemplate.update(sql, paramMap);
    }

    private void updateCustomer(Customer customer) {
        String sql = "INSERT INTO customer (" +
                "customer_code, customer_name, customer_kana, customer_pass, customer_birth, customer_job, " +
                "customer_mail, customer_tel, customer_post, customer_add" +
                ") VALUES (" +
                ":customerCode, :customerName, :customerKana, :customerPass, :customerBirth, :customerJob, " +
                ":customerMail, :customerTel, :customerPost, :customerAdd)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerCode", customer.getCustomerCode());
        paramMap.put("customerName", customer.getCustomerName());
        paramMap.put("customerKana", customer.getCustomerKana());
        paramMap.put("customerPass", customer.getCustomerPass());
        paramMap.put("customerBirth", customer.getCustomerBirth());
        paramMap.put("customerJob", customer.getCustomerJob());
        paramMap.put("customerMail", customer.getCustomerMail());
        paramMap.put("customerTel", customer.getCustomerTel());
        paramMap.put("customerPost", customer.getCustomerPost());
        paramMap.put("customerAdd", customer.getCustomerAdd());

        jdbcTemplate.update(sql, paramMap);
    }

    private void updateTourinfo(Tourinfo tourinfo) {
        String sql = "INSERT INTO tourinfo (" +
                "tour_code, planned_day, plan_no, tour_name, tour_days, dep_day, ava_rec_max, dep_code, arr_code, " +
                "accom_code, base_price, conductor, tour_abs" +
                ") VALUES (" +
                ":tourCode, :plannedDay, :planNo, :tourName, :tourDays, :depDay, :avaRecMax, :depCode, :arrCode, "  +
                ":accomCode, :basePrice, :conductor, :tourAbs)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("tourCode", tourinfo.getTourCode());
        paramMap.put("plannedDay", tourinfo.getPlannedDay());
        paramMap.put("planNo", tourinfo.getPlanNo());
        paramMap.put("tourName", tourinfo.getTourName());
        paramMap.put("tourDays", tourinfo.getTourDays());
        paramMap.put("depDay", tourinfo.getDepDay());
        paramMap.put("avaRecMax", tourinfo.getAvaRecMax());
        paramMap.put("depCode", tourinfo.getDeparture().getDepCode());
        paramMap.put("arrCode", tourinfo.getArrival().getArrCode());
        paramMap.put("accomCode", tourinfo.getAccommodation().getAccomCode());
        paramMap.put("basePrice", tourinfo.getBasePrice());
        paramMap.put("conductor", tourinfo.getConductor());
        paramMap.put("tourAbs", tourinfo.getTourAbs());

        jdbcTemplate.update(sql, paramMap);
    }

    private void updateReserve(Reserve reserve) {
        String sql = "INSERT INTO reserve(" +
                "reserve_no, tour_code, reserved_day, adult_count, child_count, customer_code, transfer, sum_price, remarks" +
                ") VALUES (" +
                ":reserveNo, :tourCode, :reservedDay, :adultCount, :childCount, :customerCode, :transfer, :sumPrice, :remarks)";

        Map<String, Object> paramMap = new HashMap<>();
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
