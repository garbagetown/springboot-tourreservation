package garbagetown.app.managereservation;

import garbagetown.domain.model.Reserve;
import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.service.reserve.ReserveService;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import garbagetown.domain.service.tourinfo.PriceCalculateSharedService;
import garbagetown.domain.service.tourinfo.TourinfoSharedService;
import garbagetown.domain.service.userdetails.ReservationUserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by garbagetown on 10/27/15.
 */
@Component
public class ManageReservationHelper {

    @Inject
    ReserveService reserveService;

    @Inject
    PriceCalculateSharedService priceCalculateService;

    @Inject
    TourinfoSharedService tourInfoSharedService;

    public List<ReserveRowOutput> list(ReservationUserDetails userDetails) {

        String customerCode = userDetails.getUsername();

        List<Reserve> reserves = reserveService.findAllByCustomerCode(customerCode);

        List<ReserveRowOutput> rows = new ArrayList<ReserveRowOutput>();
        for (Reserve reservation : reserves) {
            ReserveRowOutput output = new ReserveRowOutput();
            Tourinfo tourinfo = reservation.getTourinfo();

            output.setLimitExceeding(tourInfoSharedService.isOverPaymentLimit(tourinfo));
            output.setTourDays(Integer.toString(tourinfo.getTourDays()));
            output.setReserve(reservation);
            rows.add(output);
        }
        return rows;
    }

    public ReservationDetailOutput findDetail(String reserveNo) {

        Reserve reserve = reserveService.findOne(reserveNo);
        Tourinfo tourinfo = reserve.getTourinfo();
        int adultCount = reserve.getAdultCount();
        int childCount = reserve.getChildCount();

        ReservationDetailOutput output = new ReservationDetailOutput();
        output.setReserve(reserve);
        output.setCustomer(reserve.getCustomer());

        PriceCalculateOutput price = priceCalculateService.calculatePrice(tourinfo.getBasePrice(),
                adultCount, childCount);
        output.setPriceCalculateOutput(price);

        output.setPaymentTimeLimit(tourinfo.getPaymentLimit().toDate());
        output.setLimitExceeding(tourInfoSharedService.isOverPaymentLimit(tourinfo));

        return output;
    }

    public ReservationDetailOutput findDetail(String reserveNo, ManageReservationForm form) {

        ReservationDetailOutput output = findDetail(reserveNo);

        Tourinfo tourinfo = output.getReserve().getTourinfo();

        PriceCalculateOutput price = priceCalculateService.calculatePrice(tourinfo.getBasePrice(),
                form.getAdultCount(), form.getChildCount());

        output.setPriceCalculateOutput(price);

        return output;
    }
}
