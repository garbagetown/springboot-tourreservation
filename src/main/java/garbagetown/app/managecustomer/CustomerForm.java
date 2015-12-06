package garbagetown.app.managecustomer;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Created by garbagetown on 10/9/15.
 */
@Data
public class CustomerForm implements Serializable {

    @NotEmpty
    @Size(max = 20)
    @Pattern(regexp = "^[ァ-ヶ]+$")
    private String customerKana;

    @NotEmpty
    @Size(max = 20)
    @Pattern(regexp = "[^ -~｡-ﾟ]*")
    private String customerName;

    @NotNull
    @Min(1900)
    private Integer customerBirthYear;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer customerBirthMonth;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer customerBirthDay;

    @NotEmpty
    @Size(max = 40)
    private String customerJob;

    @Email
    @Size(max = 30)
    private String customerMail;

    @Pattern(regexp = "[0-9-]+")
    @Size(min = 10,max = 13)
    private String customerTel;

    @Pattern(regexp = "[0-9]{3}-[0-9]{4}")
    private String customerPost;

    @NotEmpty
    private String customerAdd;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z]+")
    @Size(min = 4,max = 20)
    private String customerPass;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z]+")
    @Size(min = 4,max = 20)
    private String customerPassConfirm;
}
