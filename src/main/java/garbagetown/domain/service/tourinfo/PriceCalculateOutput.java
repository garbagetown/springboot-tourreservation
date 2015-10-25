package garbagetown.domain.service.tourinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by garbagetown on 10/25/15.
 */
@Data
public class PriceCalculateOutput implements Serializable {

    private Integer adultUnitPrice;
    private Integer childUnitPrice;
    private Integer adultCount;
    private Integer childCount;
    private Integer adultPrice;
    private Integer childPrice;
    private Integer sumPrice;
}
