package garbagetown.app.tour;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by garbagetown on 10/13/15.
 */
@Data
public class TourForm implements Serializable {

    @NotNull
    private Integer depYear;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer depMonth;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer depDay;

    @NotNull
    private Integer tourDays;

    @NotEmpty
    private String depCode;

    @NotEmpty
    private String arrCode;

    @NotNull
    @Min(0)
    private Integer adultCount;

    @NotNull
    @Min(0)
    private Integer childCount;

    @NotNull
    @Min(0)
    private Integer basePrice;
}
