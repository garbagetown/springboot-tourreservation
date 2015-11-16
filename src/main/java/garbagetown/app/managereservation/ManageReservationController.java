package garbagetown.app.managereservation;

import garbagetown.domain.model.Reserve;
import garbagetown.domain.service.reserve.ReservationUpdateInput;
import garbagetown.domain.service.reserve.ReservationUpdateOutput;
import garbagetown.domain.service.reserve.ReserveService;
import garbagetown.domain.service.userdetails.ReservationUserDetails;
import org.dozer.Mapper;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by garbagetown on 10/27/15.
 */
@Controller
@RequestMapping(value = "reservations")
public class ManageReservationController {

    @Inject
    ManageReservationHelper helper;

    @Inject
    ReserveService service;

    @Inject
    Mapper mapper;

    @ModelAttribute
    public ManageReservationForm setUpForm() {
        return new ManageReservationForm();
    }

    @RequestMapping(value="me", method = RequestMethod.GET)
    public String list(@AuthenticationPrincipal ReservationUserDetails userDetails ,Model model) {
        List<ReserveRowOutput> rows = helper.list(userDetails);

        model.addAttribute("rows", rows);
        return "managereservation/list";
    }

    @RequestMapping(value = "{reserveNo}", method = RequestMethod.GET)
    public String detailForm(@PathVariable("reserveNo") String reserveNo, Model model) {
        ReservationDetailOutput output = helper.findDetail(reserveNo);

        model.addAttribute("output", output);
        return "managereservation/detailForm";
    }

    @RequestMapping(value = "{reserveNo}/update", method = RequestMethod.GET, params = "form")
    public String updateForm(@PathVariable("reserveNo") String reserveNo, ManageReservationForm form, Model model) {
        Reserve reserve = service.findOne(reserveNo);

        mapper.map(reserve, form);

        model.addAttribute(reserve);
        return "managereservation/updateForm";
    }

    @RequestMapping(value = "{reserveNo}/update", method = RequestMethod.POST, params = "confirm")
    public String updateConfirm(@PathVariable("reserveNo") String reserveNo, @Validated ManageReservationForm form,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            updateRedo(reserveNo, form, model);
        }
        ReservationDetailOutput output = helper.findDetail(reserveNo, form);
        model.addAttribute("output", output);
        return "managereservation/updateConfirm";
    }

    @RequestMapping(value = "{reserveNo}/update", method = RequestMethod.POST, params = "redo")
    public String updateRedo(@PathVariable("reserveNo") String reserveNo, ManageReservationForm form, Model model) {
        Reserve reserve = service.findOne(reserveNo);
        model.addAttribute(reserve);
        return "managereservation/updateForm";
    }

    @RequestMapping(value = "{reserveNo}/update", method = RequestMethod.POST)
    public String update(@PathVariable("reserveNo") String reserveNo, @Validated ManageReservationForm form,
                         BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            updateRedo(reserveNo, form, model);
        }

        ReservationUpdateInput input = mapper.map(form, ReservationUpdateInput.class);
        input.setReserveNo(reserveNo);

        ReservationUpdateOutput output = service.update(input);
        redirectAttributes.addFlashAttribute("output", output);

        return "redirect:/reservations/{reserveNo}/update?complete";
    }

    @RequestMapping(value = "{reserveNo}/update", method = RequestMethod.GET, params = "complete")
    public String updateComplete() {
        return "managereservation/updateComplete";
    }

    @RequestMapping(value = {"{reserveNo}/update", "{reserveNo}/cancel"}, method = RequestMethod.POST,
            params = "backToList")
    public String backToList() {
        return "redirect:/reservations/me";
    }

    @RequestMapping(value = "{reserveNo}/cancel", method = RequestMethod.GET)
    public String cancelConfirm(@PathVariable("reserveNo") String reserveNo, Model model) {
        ReservationDetailOutput output = helper.findDetail(reserveNo);

        model.addAttribute("output", output);
        return "managereservation/cancelConfirm";
    }

    @RequestMapping(value = "{reserveNo}/cancel", method = RequestMethod.POST)
    public String cancel(@PathVariable("reserveNo") String reserveNo, Model model) {
        try {
            service.cancel(reserveNo);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return cancelConfirm(reserveNo, model);
        }

        return "redirect:/reservations/{reserveNo}/cancel?complete";
    }

    @RequestMapping(value = "{reserveNo}/cancel", method = RequestMethod.GET, params = "complete")
    public String cancelComplete(@PathVariable("reserveNo") String reserveNo, Model model) {
        model.addAttribute("reserveNo", reserveNo);
        return "managereservation/cancelComplete";
    }
}
