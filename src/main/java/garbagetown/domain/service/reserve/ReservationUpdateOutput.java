package garbagetown.domain.service.reserve;

import garbagetown.domain.model.Reserve;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import lombok.Data;

import java.util.Date;

/**
 * Created by garbagetown on 11/15/15.
 */
@Data
public class ReservationUpdateOutput {
    private Reserve reserve;
    private Date paymentTimeLimit;
    private PriceCalculateOutput priceCalculateOutput;
}
