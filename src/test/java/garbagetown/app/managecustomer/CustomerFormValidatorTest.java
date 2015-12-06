package garbagetown.app.managecustomer;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.FieldError;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by garbagetown on 12/7/15.
 */
public class CustomerFormValidatorTest {

    CustomerFormValidator customerFormValidator;
    CustomerForm customerForm;

    @Before
    public void setUp() {
        customerFormValidator = new CustomerFormValidator();
        customerForm = new CustomerForm();

        DateTime now = DateTime.now();
        customerForm.setCustomerBirthYear(now.getYear() - 20);
        customerForm.setCustomerBirthMonth(now.getMonthOfYear());
        customerForm.setCustomerBirthDay(now.getDayOfMonth());

        String password = "password";
        customerForm.setCustomerPass(password);
        customerForm.setCustomerPassConfirm(password);
    }

    @Test
    public void testValidate() {
        BindingResult result = new DirectFieldBindingResult(customerForm, "customerForm");

        customerFormValidator.validate(customerForm, result);

        assertThat(result.hasErrors(), is(false));
    }

    @Test
    public void testValidate_validLeapYear() {
        customerForm.setCustomerBirthYear(1996);
        customerForm.setCustomerBirthMonth(2);
        customerForm.setCustomerBirthDay(29);

        BindingResult result = new DirectFieldBindingResult(customerForm, "customerForm");

        customerFormValidator.validate(customerForm, result);

        assertThat(result.hasErrors(), is(false));
    }

    @Test
    public void testValidate_invalidDate() {
        customerForm.setCustomerBirthYear(1997);
        customerForm.setCustomerBirthMonth(2);
        customerForm.setCustomerBirthDay(29);

        BindingResult result = new DirectFieldBindingResult(customerForm, "customerForm");

        customerFormValidator.validate(customerForm, result);

        assertThat(result.hasErrors(), is(true));

        FieldError error = result.getFieldError("customerBirthYear");
        assertThat(error, is(notNullValue()));
        assertThat(error.getCode(), is("IncorrectDate.customerBirth"));
        assertThat(error.getDefaultMessage(), is("Incorrect date was entered."));
    }

    @Test
    public void testValidate_invalidPasswordConfirm() {
        customerForm.setCustomerPassConfirm("invalidPassword");

        BindingResult result = new DirectFieldBindingResult(customerForm, "customerForm");

        customerFormValidator.validate(customerForm, result);

        assertThat(result.hasErrors(), is(true));

        assertCustomerPassError(result.getFieldError("customerPass"));
    }

    private void assertCustomerPassError(FieldError error) {
        assertThat(error, is(notNullValue()));
        assertThat(error.getCode(), is("NotEquals.customerPass"));
        assertThat(error.getDefaultMessage(), is("customerPass and CustomerPassConfirm are not same."));

    }

    @Test
    public void testValidate_nullPassword() {
        customerForm.setCustomerPass(null);

        BindingResult result = new DirectFieldBindingResult(customerForm, "customerForm");

        customerFormValidator.validate(customerForm, result);

        assertThat(result.hasErrors(), is(true));

        assertCustomerPassError(result.getFieldError("customerPass"));
    }

    @Test
    public void testValidate_nullPasswordConfirm() {
        customerForm.setCustomerPassConfirm(null);

        BindingResult result = new DirectFieldBindingResult(customerForm, "customerForm");

        customerFormValidator.validate(customerForm, result);

        assertThat(result.hasErrors(), is(true));

        assertCustomerPassError(result.getFieldError("customerPass"));
    }
}
