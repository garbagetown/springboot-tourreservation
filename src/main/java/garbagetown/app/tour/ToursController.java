package garbagetown.app.tour;

import garbagetown.domain.model.Tourinfo;
import garbagetown.domain.repository.tourinfo.TourinfoCriteria;
import garbagetown.domain.service.tourinfo.TourinfoService;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by garbagetown on 10/7/15.
 */
@Controller
@RequestMapping(value = "tours")
@SessionAttributes(types = TourForm.class)
public class ToursController {

    @Inject
    TourinfoService service;

    @Inject
    JodaTimeDateFactory dateFactory;

    @Inject
    TourFormValidator validator;

    @Inject
    Mapper beanMapper;

    @InitBinder("tourForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @ModelAttribute
    TourForm setUpForm() {
        TourForm form = new TourForm();
        DateTime dateTime = dateFactory.newDateTime();
        DateTime nextWeekDate = dateTime.plusWeeks(1);
        form.setDepYear(nextWeekDate.getYear());
        form.setDepMonth(nextWeekDate.getMonthOfYear());
        form.setDepDay(nextWeekDate.getDayOfMonth());
        return form;
    }

    @RequestMapping(method = RequestMethod.GET, params = "initForm")
    public String index(SessionStatus status) {
        status.setComplete();
        return "redirect:/tours?form";
    }

    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String form() {
        return "tours/form";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(@Validated TourForm form, BindingResult result, Model model,
                         @PageableDefault Pageable pageable) {

        if (result.hasErrors()) {
            return "tours/form";
        }

        TourinfoCriteria criteria = beanMapper.map(form, TourinfoCriteria.class);

        Date depDate = new LocalDate(form.getDepYear(), form.getDepMonth(), form.getDepDay()).toDate();
        criteria.setDepDate(depDate);

        Page<Tourinfo> page = service.search(criteria, pageable);
        model.addAttribute("page", page);
        model.addAttribute("form", form);

        return "tours/form";
    }
}
