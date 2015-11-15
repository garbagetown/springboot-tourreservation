package garbagetown.domain.service.reserve;

import garbagetown.domain.model.Reserve;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.List;

/**
 * Created by garbagetown on 10/27/15.
 */
public interface ReserveService {

    List<Reserve> findAllByCustomerCode(String customerCode);

    Reserve findOne(String reserveNo);

    ReserveTourOutput reserve(ReserveTourInput input) throws BusinessException;

    ReservationUpdateOutput update(ReservationUpdateInput input) throws BusinessException;

    void cancel(String reserveNo);
}
