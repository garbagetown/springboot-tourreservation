package garbagetown.domain.service.tourinfo;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.tourinfo.TourinfoCriteria;
import garbagetown.domain.repository.tourinfo.TourinfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Created by garbagetown on 10/15/15.
 */
@Service
@Transactional
public class TourinfoServiceImpl implements TourinfoService {

    @Inject
    TourinfoRepository tourinfoRepository;

    @Override
    public Page<Tourinfo> search(TourinfoCriteria criteria, Pageable pageable) {
        return tourinfoRepository.findPageBySearchCriteria(criteria, pageable);
    }
}
