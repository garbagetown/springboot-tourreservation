package garbagetown.domain.service.reserve;

import garbagetown.domain.model.Customer;
import lombok.Data;

/**
 * Created by garbagetown on 11/6/15.
 */
@Data
public class ReserveTourInput {
    private String tourCode;
    private Integer adultCount;
    private Integer childCount;
    private String remarks;
    private Customer customer;
}
