package garbagetown.app.reservetour;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by garbagetown on 10/24/15.
 */
@Data
public class ReserveTourForm implements Serializable {

    @NotNull
    @Min(0)
    @Max(5)
    private Integer adultCount;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer childCount;

    @Size(min = 0, max = 80)
    private String remarks;
}
