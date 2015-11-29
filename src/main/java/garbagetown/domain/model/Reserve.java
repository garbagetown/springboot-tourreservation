package garbagetown.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yu-umezawa on 2015/10/15.
 */
@Data
@ToString(exclude = {"customer","tourInfo"})
@EqualsAndHashCode(exclude = {"customer","tourInfo"})
@NoArgsConstructor
@Entity
@Table(name = "reserve")
public class Reserve implements Serializable {

    public static final String TRANSFERED = "1";
    public static final String NOT_TRANSFERED = "0";

    @Id
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "reserve_no", columnDefinition = "char")
    private String reserveNo;

    @NotNull
    @Column(name = "reserved_day")
    @Temporal(TemporalType.DATE)
    private Date reservedDay;

    @NotNull
    @Column(name = "adult_count", columnDefinition = "decimal")
    private int adultCount;

    @NotNull
    @Column(name = "child_count", columnDefinition = "decimal")
    private int childCount;

    @NotNull
    @Column(name = "transfer", columnDefinition = "char")
    private String transfer;

    @NotNull
    @Column(name = "sum_price", columnDefinition = "decimal")
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