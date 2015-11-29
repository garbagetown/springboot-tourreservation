package garbagetown.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by yu-umezawa on 2015/10/15.
 */
@Data
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "staff_code", columnDefinition = "char")
    private String staffCode;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "staff_name")
    private String staffName;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "staff_kana")
    private String staffKana;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "staff_pass")
    private String staffPass;
}
