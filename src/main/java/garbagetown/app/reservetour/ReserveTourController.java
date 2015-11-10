package garbagetown.app.reservetour;

import garbagetown.domain.service.reserve.ReserveTourOutput;
import garbagetown.domain.service.userdetails.ReservationUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.exception.BusinessException;

import javax.inject.Inject;

/**
 * Created by garbagetown on 10/24/15.
 */
@Slf4j
@Controller
@RequestMapping(value = "tours")
public class ReserveTourController {

    @Inject
    ReserveTourHelper reserveTourHelper;

    @RequestMapping(value = {"{tourCode}", "{tourCode}/reserve"}, method = RequestMethod.GET, params = "form")
    public String reserveForm(@AuthenticationPrincipal ReservationUserDetails userDetails,
                              @PathVariable("tourCode") String tourCode, ReserveTourForm form, Model model) {

        TourDetailOutput output = reserveTourHelper.findTourDetail(userDetails, tourCode, form);

        model.addAttribute("output", output);

        return "reservetour/reserveForm";
    }

    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.POST, params = "confirm")
    public String confirm(@AuthenticationPrincipal ReservationUserDetails userDetails,
                          @PathVariable("tourCode") String tourCode, @Validated ReserveTourForm form,
                          BindingResult result, Model model) {

        if (result.hasErrors()) {
            return reserveForm(userDetails, tourCode, form, model);
        }

        TourDetailOutput output = reserveTourHelper.findTourDetail(userDetails, tourCode, form);

        model.addAttribute("output", output);

        return "reservetour/reserveConfirm";
    }

    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.POST)
    public String reserve(@AuthenticationPrincipal ReservationUserDetails userDetails,
                          @PathVariable("tourCode") String tourCode, @Validated ReserveTourForm form,
                          BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return reserveForm(userDetails, tourCode, form, model);
        }

        try {
            ReserveTourOutput output = reserveTourHelper.reserve(userDetails, tourCode, form);
            redirectAttributes.addFlashAttribute("output", output);
        } catch (BusinessException e) {
            model.addAttribute(e.getResultMessages());
            return reserveForm(userDetails, tourCode, form, model);
        }

        return "redirect:/tours/{tourCode}/reserve?complete";
    }

    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.GET, params = "complete")
    public String reserveComplete() {
        return "reservetour/reserveComplete";
    }

    @RequestMapping(value = "{tourCode}/reserve", method = RequestMethod.POST, params = "redo")
    public String reserveRedo(@AuthenticationPrincipal ReservationUserDetails userDetails,
                              @PathVariable("tourCode") String tourCode,
                              ReserveTourForm form, Model model) {
        return reserveForm(userDetails, tourCode, form, model);
    }
}
