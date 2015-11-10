package garbagetown.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yu-umezawa on 2015/10/15.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "reserve")
public class Reserve implements Serializable {

    public static final String TRANSFERED = "1";
    public static final String NOT_TRANSFERED = "0";

    @Id
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "reserve_no")
    private String reserveNo;

    @NotNull
    @Column(name = "reserved_day")
    @Temporal(TemporalType.DATE)
    private Date reservedDay;

    @NotNull
    @Column(name = "adult_count")
    private int adultCount;

    @NotNull
    @Column(name = "child_count")
    private int childCount;

    @NotNull
    @Column(name = "transfer")
    private String transfer;

    @NotNull
    @Column(name = "sum_price")
    private int sumPrice;

    @Size(max = 1000)
    @Column(name = "remarks")
    private String remarks;

    @JoinColumn(name = "customer_code", referencedColumnName = "customer_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;

    @JoinColumn(name = "tour_code", referencedColumnName = "tour_code")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tourinfo tourinfo;
}