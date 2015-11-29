package garbagetown.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by yu-umezawa on 2015/10/15.
 */
@Data
@Entity
@Table(name = "age")
public class Age implements Serializable {

    @Id
    @NotNull
    @Size(max = 1)
    @Column(name = "age_code", columnDefinition = "char")
    private String ageCode;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "age_name")
    private String ageName;

    @NotNull
    @Column(name = "age_rate", columnDefinition = "decimal")
    private int ageRate;
}
