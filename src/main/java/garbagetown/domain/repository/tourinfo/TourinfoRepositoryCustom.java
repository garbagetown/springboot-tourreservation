package garbagetown.domain.repository.tourinfo;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.tourinfo.TourinfoCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by garbagetown on 10/19/15.
 */
public interface TourinfoRepositoryCustom {
    Page<Tourinfo> findPageBySearchCriteria(TourinfoCriteria criteria, Pageable pageable);
}
