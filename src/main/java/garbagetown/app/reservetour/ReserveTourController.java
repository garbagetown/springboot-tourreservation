package garbagetown.app.reservetour;

import garbagetown.domain.service.userdetails.ReservationUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "{tourCode}", method = RequestMethod.GET, params = "form")
    public String reserveForm(@AuthenticationPrincipal ReservationUserDetails userDetails,
                              @PathVariable("tourCode") String tourCode, ReserveTourForm form, Model model) {

        TourDetailOutput output = reserveTourHelper.findTourDetail(userDetails, tourCode, form);

        model.addAttribute("output", output);

        return "reservetour/reserveForm";
    }
}
