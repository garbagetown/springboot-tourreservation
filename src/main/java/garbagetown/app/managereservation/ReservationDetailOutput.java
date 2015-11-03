package garbagetown.app.managereservation;

import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Reserve;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import lombok.Data;

import java.util.Date;

/**
 * Created by garbagetown on 11/3/15.
 */
@Data
public class ReservationDetailOutput {

    private PriceCalculateOutput priceCalculateOutput;
    private Reserve reserve;
    private Customer customer;
    private Date paymentTimeLimit;
    private Boolean limitExceeding;
}
