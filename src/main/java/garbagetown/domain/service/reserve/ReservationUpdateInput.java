package garbagetown.domain.service.reserve;

import lombok.Data;

/**
 * Created by garbagetown on 11/15/15.
 */
@Data
public class ReservationUpdateInput {
    private String reserveNo;
    private Integer adultCount;
    private Integer childCount;
}
