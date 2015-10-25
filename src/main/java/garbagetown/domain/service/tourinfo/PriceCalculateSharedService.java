package garbagetown.domain.service.tourinfo;

/**
 * Created by garbagetown on 10/25/15.
 */
public interface PriceCalculateSharedService {

    PriceCalculateOutput calculatePrice(Integer basePrice, Integer adultCount, Integer childCount);
}
