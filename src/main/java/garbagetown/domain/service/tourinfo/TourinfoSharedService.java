package garbagetown.domain.service.tourinfo;

import garbagetown.domain.model.Tourinfo;

/**
 * Created by garbagetown on 10/25/15.
 */
public interface TourinfoSharedService {

    Tourinfo findOne(String tourCode);

    Tourinfo findOneForUpdate(String tourCode);

    Boolean isOverPaymentLimit(Tourinfo tourinfo);
}
