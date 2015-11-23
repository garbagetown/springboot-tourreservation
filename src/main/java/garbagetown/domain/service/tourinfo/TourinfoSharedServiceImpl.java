package garbagetown.domain.service.tourinfo;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.tourinfo.TourinfoRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Created by garbagetown on 10/25/15.
 */
@Service
@Transactional
public class TourinfoSharedServiceImpl implements TourinfoSharedService {

    @Inject
    TourinfoRepository repository;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Override
    public Tourinfo findOne(String tourCode) {
        return repository.findOne(tourCode);
    }

    @Override
    public Tourinfo findOneForUpdate(String tourCode) {
        return repository.findOneForUpdate(tourCode);
    }

    @Override
    public Boolean isOverPaymentLimit(Tourinfo tourinfo) {
        Assert.notNull(tourinfo, "tour must not be null");

        DateTime today = dateFactory.newDateTime().withTime(0, 0, 0, 0);
        DateTime paymentLimit = tourinfo.getPaymentLimit();

        return today.isAfter(paymentLimit);
    }
}
