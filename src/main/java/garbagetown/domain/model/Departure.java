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
@Table(name = "departure")
public class Departure implements Serializable {

    @Id
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "dep_code", columnDefinition = "char")
    private String depCode;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "dep_name")
    private String depName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departure")
    private List<Tourinfo> tourinfo;
}
