package garbagetown.domain.service.tourinfo;

import garbagetown.domain.model.Age;
import garbagetown.domain.repository.age.AgeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by garbagetown on 10/25/15.
 */
@Service
public class PriceCalculateSharedServiceImpl implements PriceCalculateSharedService {

    @Inject
    AgeRepository repository;

    private Age adultAge;
    private Age childAge;

    @Override
    public PriceCalculateOutput calculatePrice(Integer basePrice, Integer adultCount, Integer childCount) {

        PriceCalculateOutput result = new PriceCalculateOutput();

        int adultUnitPrice = basePrice * adultAge.getAgeRate() / 100;
        result.setAdultUnitPrice(adultUnitPrice);

        int adultPrice = adultCount * adultUnitPrice;
        result.setAdultPrice(adultPrice);

        int childUnitPrice = basePrice * childAge.getAgeRate() / 100;
        result.setChildUnitPrice(childUnitPrice);

        int childPrice = childCount * childUnitPrice;
        result.setChildPrice(childPrice);

        result.setAdultCount(adultCount);
        result.setChildCount(childCount);
        result.setSumPrice(adultPrice + childPrice);

        return result;
    }

    @PostConstruct
    public void loadAges() {
        adultAge = repository.findOne("0");
        childAge = repository.findOne("1");
    }
}
