package garbagetown.domain.service.reserve;

import garbagetown.app.common.constants.MessageKeys;
import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Reserve;
import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.reserve.ReserveRepository;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import garbagetown.domain.service.tourinfo.PriceCalculateSharedService;
import garbagetown.domain.service.tourinfo.TourinfoSharedService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.sequencer.Sequencer;

import javax.inject.Inject;
import javax.inject.Named;
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

    @Inject
    TourinfoSharedService tourinfoSharedService;

    @Inject
    PriceCalculateSharedService priceCalculateSharedService;

    @Inject
    Mapper mapper;

    @Inject
    @Named("reserveNoSeq")
    Sequencer<String> reserveNoSeq;

    @Inject
    JodaTimeDateFactory dateFactory;

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

    @Override
    public ReserveTourOutput reserve(ReserveTourInput input) throws BusinessException {

        Tourinfo tourinfo = tourinfoSharedService.findOneForUpdate(input.getTourCode());

        if (tourinfoSharedService.isOverPaymentLimit(tourinfo)) {
            throw new BusinessException(ResultMessages.error().add(MessageKeys.E_TR_0004));
        }

        if (vacants(tourinfo) < input.getAdultCount() + input.getChildCount()) {
            throw new BusinessException(ResultMessages.error().add(MessageKeys.E_TR_0005));
        }

        PriceCalculateOutput priceCalculateOutput = priceCalculateSharedService.calculatePrice(
                tourinfo.getBasePrice(), input.getAdultCount(), input.getChildCount());

        Reserve reserve = mapper.map(input, Reserve.class);
        reserve.setReserveNo(reserveNoSeq.getNext());
        reserve.setReservedDay(dateFactory.newDateTime().withTime(0, 0, 0, 0).toDate());
        reserve.setTransfer(Reserve.NOT_TRANSFERED);
        reserve.setSumPrice(priceCalculateOutput.getSumPrice());
        reserve.setRemarks(input.getRemarks());
        reserve.setCustomer(input.getCustomer());
        reserve.setTourinfo(tourinfo);

        reserveRepository.save(reserve);

        ReserveTourOutput output = new ReserveTourOutput();
        output.setPriceCalculateOutput(priceCalculateOutput);
        output.setReserve(reserve);
        output.setCustomer(input.getCustomer());
        output.setTourinfo(tourinfo);
        output.setPaymentTimeLimit(tourinfo.getPaymentLimit().toDate());

        // fetch to avoid LazyInitializationException
        tourinfo.getAccommodation().getAccomCode();
        tourinfo.getDeparture().getDepCode();
        tourinfo.getArrival().getArrCode();

        return output;
    }

    @Override
    public ReservationUpdateOutput update(ReservationUpdateInput input) throws BusinessException {

        // TODO check BusinessException

        Reserve reserve = findOne(input.getReserveNo());

        mapper.map(input, reserve, "reserve_map_nonnull");

        Tourinfo tourinfo = reserve.getTourinfo();

        PriceCalculateOutput priceCalculateOutput = priceCalculateSharedService.calculatePrice(
                tourinfo.getBasePrice(), input.getAdultCount(), input.getChildCount());

        reserve.setSumPrice(priceCalculateOutput.getSumPrice());
        reserveRepository.save(reserve);

        ReservationUpdateOutput output = new ReservationUpdateOutput();
        output.setReserve(reserve);
        output.setPaymentTimeLimit(tourinfo.getPaymentLimit().toDate());
        output.setPriceCalculateOutput(priceCalculateOutput);

        // fetch to avoid LazyInitializationException
        tourinfo.getDeparture().getDepCode();
        tourinfo.getArrival().getArrCode();

        return output;
    }

    @Override
    public void cancel(String reserveNo) {

        Reserve reserve = reserveRepository.findOneForUpdate(reserveNo);

        if (Reserve.TRANSFERED.equals(reserve.getTransfer())) {
            throw new BusinessException(ResultMessages.error().add(MessageKeys.E_TR_0001));
        }
        if (tourinfoSharedService.isOverPaymentLimit(reserve.getTourinfo())) {
            throw new BusinessException(ResultMessages.error().add(MessageKeys.E_TR_0002));
        }

        if (reserve != null) {
            reserveRepository.delete(reserveNo);
        } else {
            throw new BusinessException(ResultMessages.error().add(MessageKeys.E_TR_0003));
        }
    }

    private long vacants(Tourinfo tourinfo) {
        int max = tourinfo.getAvaRecMax();
        Long reserved = reserveRepository.countReservedPersonSumByTourinfo(tourinfo);
        if (reserved  == null) {
            reserved = 0L;
        }
        return max - reserved;
    }
}
