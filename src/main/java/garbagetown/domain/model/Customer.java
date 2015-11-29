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
import java.util.List;

/**
 * Created by garbagetown on 10/10/15.
 */
@Data
@ToString(exclude = "reserves")
@EqualsAndHashCode(exclude = "reserves")
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "customer_code", columnDefinition = "char")
    private String customerCode;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customer_name")
    private String customerName;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customer_kana")
    private String customerKana;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "customer_pass")
    private String customerPass;

    @NotNull
    @Column(name = "customer_birth", columnDefinition = "date")
    private Date customerBirth;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customer_job")
    private String customerJob;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "customer_mail")
    private String customerMail;

    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "customer_tel")
    private String customerTel;

    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "customer_post")
    private String customerPost;

    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "customer_add")
    private String customerAdd;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Reserve> reserves;

    public Customer(String customerCode) {
        this.customerCode = customerCode;
    }
}
