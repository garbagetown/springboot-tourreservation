package garbagetown.domain.repository;

import garbagetown.domain.model.Tourinfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by garbagetown on 10/15/15.
 */
public interface TourinfoRepository extends JpaRepository<Tourinfo, String>, TourinfoRepositoryCustom {
}
