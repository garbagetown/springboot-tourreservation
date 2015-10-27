package garbagetown.app.managereservation;

import garbagetown.domain.model.Reserve;
import lombok.Data;

/**
 * Created by garbagetown on 10/27/15.
 */
@Data
public class ReserveRowOutput {
    private Reserve reserve;
    private Boolean limitExceeding;
    private String tourDays;
}
