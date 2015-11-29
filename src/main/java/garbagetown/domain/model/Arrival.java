package garbagetown.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yu-umezawa on 2015/10/15.
 */
@Data
@Entity
@Table(name = "arrival")
public class Arrival implements Serializable {

    @Id
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "arr_code", columnDefinition = "char")
    private String arrCode;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "arr_name")
    private String arrName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "arrival")
    private List<Tourinfo> tourinfo;
}
