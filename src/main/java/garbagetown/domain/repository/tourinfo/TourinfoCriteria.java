package garbagetown.domain.repository.tourinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by garbagetown on 10/15/15.
 */
@Data
public class TourinfoCriteria implements Serializable {

    private Date depDate;
    private Integer tourDays;
    private String depCode;
    private String arrCode;
    private Integer adultCount;
    private Integer childCount;
    private Integer basePrice;
}
