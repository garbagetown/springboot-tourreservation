package garbagetown.domain.service;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.TourinfoCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by garbagetown on 10/15/15.
 */
public interface TourinfoService {
    Page<Tourinfo> search(TourinfoCriteria criteria, Pageable pageable);
}
