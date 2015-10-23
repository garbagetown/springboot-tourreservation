package garbagetown.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@Entity
@Table(name = "tourinfo")
public class Tourinfo implements Serializable {

    @Id
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tour_code")
    private String tourCode;

    @NotNull
    @Column(name = "planned_day")
    @Temporal(TemporalType.DATE)
    private Date plannedDay;

    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "plan_no")
    private String planNo;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "tour_name")
    private String tourName;

    @NotNull
    @Column(name = "tour_days")
    private int tourDays;

    @NotNull
    @Column(name = "dep_day")
    @Temporal(TemporalType.DATE)
    private Date depDay;

    @NotNull
    @Column(name = "ava_rec_max")
    private int avaRecMax;

    @NotNull
    @Column(name = "base_price")
    private int basePrice;

    @NotNull
    @Column(name = "conductor")
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
    private Depature departure;
}
