package garbagetown.domain.service.reserve;

import garbagetown.domain.model.Reserve;

import java.util.List;

/**
 * Created by garbagetown on 10/27/15.
 */
public interface ReserveService {

    List<Reserve> findAllByCustomerCode(String customerCode);
}
