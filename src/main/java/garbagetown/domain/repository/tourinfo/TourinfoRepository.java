package garbagetown.domain.repository.tourinfo;

import garbagetown.domain.model.Tourinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

/**
 * Created by garbagetown on 10/15/15.
 */
public interface TourinfoRepository extends JpaRepository<Tourinfo, String>, TourinfoRepositoryCustom {

    @Query("SELECT t FROM Tourinfo t WHERE t.tourCode = :tourCode")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Tourinfo findOneForUpdate(@Param("tourCode") String tourCode);
}
