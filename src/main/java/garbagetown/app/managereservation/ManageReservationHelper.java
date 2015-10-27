package garbagetown.app.managereservation;

import garbagetown.domain.model.Reserve;
import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.service.reserve.ReserveService;
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
}
