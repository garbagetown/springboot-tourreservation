package garbagetown.app.reservetour;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import garbagetown.domain.service.tourinfo.PriceCalculateSharedService;
import garbagetown.domain.service.tourinfo.TourinfoSharedService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by garbagetown on 10/25/15.
 */
@Component
public class ReserveTourHelper {

    @Inject
    TourinfoSharedService tourinfoSharedService;

    @Inject
    PriceCalculateSharedService priceCalculateService;

    public TourDetailOutput findTourDetail(String tourCode, ReserveTourForm form) {

        Tourinfo tourinfo = tourinfoSharedService.findOne(tourCode);

        PriceCalculateOutput priceCalculateOutput = priceCalculateService.calculatePrice(
                tourinfo.getBasePrice(), form.getAdultCount(), form.getChildCount());

        TourDetailOutput output = new TourDetailOutput();
        output.setTourinfo(tourinfo);
        output.setPriceCalculateOutput(priceCalculateOutput);

        return output;
    }
}
