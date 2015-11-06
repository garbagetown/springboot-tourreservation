package garbagetown.domain.service.reserve;

import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Reserve;
import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import lombok.Data;

import java.util.Date;

/**
 * Created by garbagetown on 11/6/15.
 */
@Data
public class ReserveTourOutput {
    private PriceCalculateOutput priceCalculateOutput;
    private Reserve reserve;
    private Customer customer;
    private Tourinfo tourinfo;
    private Date paymentTimeLimit;
}
