package garbagetown.domain.repository;

import garbagetown.domain.model.Tourinfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by garbagetown on 10/19/15.
 */
public interface TourinfoRepositoryCustom {
    Page<Tourinfo> findPageBySearchCriteria(TourinfoCriteria criteria, Pageable pageable);
}
