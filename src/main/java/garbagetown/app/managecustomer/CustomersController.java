package garbagetown.app.managecustomer;

import garbagetown.domain.model.Customer;
import garbagetown.domain.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

/**
 * Created by garbagetown on 10/7/15.
 */
@Slf4j
@Controller
@RequestMapping(value = "customers")
public class CustomersController {

    @Inject
    CustomerService service;

    @Inject
    CustomerFormValidator validator;

    @Inject
    Mapper mapper;

    @Value("${customer.initialBirthYear}")
    Integer initialBirthYear;

    @Value("${customer.initialBirthMonth}")
    Integer initialBirthMonth;

    @Value("${customer.initialBirthDay}")
    Integer initialBirthDay;

    @InitBinder("customerForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @ModelAttribute
    CustomerForm setUpForm() {
        CustomerForm form = new CustomerForm();
        form.setCustomerBirthYear(initialBirthYear);
        form.setCustomerBirthMonth(initialBirthMonth);
        form.setCustomerBirthDay(initialBirthDay);
        return form;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET, params = "form")
    public String create() {
        return "customers/form";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, params = "confirm")
    public String confirm(@Validated CustomerForm form, BindingResult result) {
        if (result.hasErrors()) {
            return redo(form);
        }
        return "customers/confirm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, params = "redo")
    public String redo(CustomerForm form) {
        return "customers/form";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String register(CustomerForm form, BindingResult result, RedirectAttributes redirect) {

        if (result.hasErrors()) {
            return redo(form);
        }

        Customer customer = mapper.map(form, Customer.class);
        customer.setCustomerBirth(new DateTime(
                form.getCustomerBirthYear(),
                form.getCustomerBirthMonth(),
                form.getCustomerBirthDay(), 0, 0, 0).toDate());

        Customer registerd = service.register(customer, form.getCustomerPass());

        redirect.addFlashAttribute(registerd);

        return "redirect:/customers/create?complete";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET, params = "complete")
    public String complete() {
        return "customers/complete";
    }
}
