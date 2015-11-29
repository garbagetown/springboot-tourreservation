package garbagetown.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yu-umezawa on 2015/10/15.
 */
@Data
@ToString(exclude = {"reserveList","accommodation","arrival","departure"})
@EqualsAndHashCode(exclude = {"reserveList","accommodation","arrival","departure"})
@NoArgsConstructor
@Entity
@Table(name = "tourinfo")
public class Tourinfo implements Serializable {

    private static final int PAYMENT_LIMIT_DAYS = 7;

    @Id
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tour_code", columnDefinition = "char")
    private String tourCode;

    @NotNull
    @Column(name = "planned_day")
    @Temporal(TemporalType.DATE)
    private Date plannedDay;

    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "plan_no", columnDefinition = "char")
    private String planNo;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "tour_name")
    private String tourName;

    @NotNull
    @Column(name = "tour_days", columnDefinition = "decimal")
    private int tourDays;

    @NotNull
    @Column(name = "dep_day")
    @Temporal(TemporalType.DATE)
    private Date depDay;

    @NotNull
    @Column(name = "ava_rec_max", columnDefinition = "decimal")
    private int avaRecMax;

    @NotNull
    @Column(name = "base_price", columnDefinition = "decimal")
    private int basePrice;

    @NotNull
    @Column(name = "conductor", columnDefinition = "char")
    private String conductor;

    @Size(max = 4000)
    @Column(name = "tour_abs")
    private String tourAbs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tourinfo")
    private List<Reserve> reserves;

    @JoinColumn(name = "accom_code", referencedColumnName = "accom_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Accommodation accommodation;

    @JoinColumn(name = "arr_code", referencedColumnName = "arr_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Arrival arrival;

    @JoinColumn(name = "dep_code", referencedColumnName = "dep_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Departure departure;

    @Transient
    public DateTime getPaymentLimit() {
        return new DateTime(depDay).minusDays(PAYMENT_LIMIT_DAYS);
    }
}
