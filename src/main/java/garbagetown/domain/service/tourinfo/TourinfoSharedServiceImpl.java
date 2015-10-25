package garbagetown.domain.service.tourinfo;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.TourinfoRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by garbagetown on 10/25/15.
 */
@Service
public class TourinfoSharedServiceImpl implements TourinfoSharedService {

    @Inject
    TourinfoRepository repository;

    @Override
    public Tourinfo findOne(String tourCode) {
        return repository.findOne(tourCode);
    }
}
