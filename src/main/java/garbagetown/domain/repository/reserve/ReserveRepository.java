package garbagetown.domain.repository.reserve;

import garbagetown.domain.model.Customer;
import garbagetown.domain.model.Reserve;
import garbagetown.domain.model.Tourinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * Created by garbagetown on 10/27/15.
 */
public interface ReserveRepository extends JpaRepository<Reserve, String> {

    @Query("SELECT r " +
            "FROM Reserve AS r " +
            "LEFT JOIN FETCH r.tourinfo AS t " +
            "LEFT JOIN FETCH t.departure " +
            "LEFT JOIN FETCH t.arrival " +
            "WHERE r.customer = :customer " +
            "ORDER BY t.depDay, r.reserveNo")
    List<Reserve> findAllByCustomer(@Param("customer") Customer customer);

    @Query("SELECT SUM(r.adultCount + r.childCount) FROM Reserve r WHERE r.tourinfo = :tourinfo")
    Long countReservedPersonSumByTourinfo(@Param("tourinfo") Tourinfo tourinfo);

    @Query("SELECT r FROM Reserve r WHERE reserveNo = :reserveNo")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Reserve findOneForUpdate(@Param("reserveNo") String reserveNo);
}
