package garbagetown.app.tour;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by garbagetown on 11/2/15.
 */
@Component
public class TourFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (TourForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TourForm form = (TourForm) target;
        try {
            new DateTime(form.getDepYear(), form.getDepMonth(), form.getDepDay(), 0, 0).toDate();
        } catch (IllegalFieldValueException e) {
            errors.rejectValue("depYear", "IncorrectDate.inputdate", "Incorrect date was entered.");
        }
    }
}
