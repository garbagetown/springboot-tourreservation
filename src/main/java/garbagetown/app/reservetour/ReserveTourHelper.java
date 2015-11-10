package garbagetown.app.reservetour;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.service.reserve.ReserveService;
import garbagetown.domain.service.reserve.ReserveTourInput;
import garbagetown.domain.service.reserve.ReserveTourOutput;
import garbagetown.domain.service.tourinfo.PriceCalculateOutput;
import garbagetown.domain.service.tourinfo.PriceCalculateSharedService;
import garbagetown.domain.service.tourinfo.TourinfoSharedService;
import garbagetown.domain.service.userdetails.ReservationUserDetails;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;
import org.terasoluna.gfw.common.exception.BusinessException;

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

    @Inject
    ReserveService reserveService;

    @Inject
    Mapper mapper;

    public TourDetailOutput findTourDetail(ReservationUserDetails userDetails, String tourCode, ReserveTourForm form) {

        Tourinfo tourinfo = tourinfoSharedService.findOne(tourCode);

        PriceCalculateOutput priceCalculateOutput = priceCalculateService.calculatePrice(
                tourinfo.getBasePrice(), form.getAdultCount(), form.getChildCount());

        TourDetailOutput output = new TourDetailOutput();
        output.setTourinfo(tourinfo);
        output.setPriceCalculateOutput(priceCalculateOutput);

        if (userDetails != null) {
            output.setCustomer(userDetails.getCustomer());
        }
        return output;
    }

    public ReserveTourOutput reserve(ReservationUserDetails userDetails, String tourCode, ReserveTourForm form)
            throws BusinessException{

        ReserveTourInput input = mapper.map(form, ReserveTourInput.class);
        input.setTourCode(tourCode);
        input.setCustomer(userDetails.getCustomer());

        ReserveTourOutput output = reserveService.reserve(input);

        return output;
    }
}
