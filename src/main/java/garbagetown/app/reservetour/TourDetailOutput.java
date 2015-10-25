package garbagetown.app.reservetour;

import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garbagetown on 10/25/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDetailOutput {

    private Tourinfo tourinfo;
    private PriceCalculateOutput priceCalculateOutput;
    private Customer customer;
}
