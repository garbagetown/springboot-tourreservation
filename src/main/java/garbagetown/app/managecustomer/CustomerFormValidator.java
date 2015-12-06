package garbagetown.app.managecustomer;

import com.google.common.base.Objects;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by garbagetown on 10/12/15.
 */
@Component
public class CustomerFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerForm form = (CustomerForm) target;
        if (!isValidBirthday(form)) {
            errors.rejectValue("customerBirthYear",
                    "IncorrectDate.customerBirth",
                    "Incorrect date was entered.");
        }
        if (!isValidPassword(form)) {
            errors.rejectValue("customerPass",
                    "NotEquals.customerPass",
                    "customerPass and CustomerPassConfirm are not same.");
        }
    }

    private boolean isValidBirthday(CustomerForm form) {
        try {
            new DateTime(form.getCustomerBirthYear(),
                    form.getCustomerBirthMonth(),
                    form.getCustomerBirthDay(), 0, 0).toDate();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isValidPassword(CustomerForm form) {
        String pass = form.getCustomerPass();
        String passConfirm = form.getCustomerPassConfirm();
        return Objects.equal(pass, passConfirm);
    }
}
