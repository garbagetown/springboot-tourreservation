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
@Table(name = "accommodation")
public class Accommodation implements Serializable {

    @Id
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "accom_code", columnDefinition = "char")
    private String accomCode;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "accom_name")
    private String accomName;

    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "accom_tel")
    private String accomTel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accommodation")
    private List<Tourinfo> tourinfo;
}
