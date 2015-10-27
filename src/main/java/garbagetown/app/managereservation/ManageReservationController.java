package garbagetown.app.managereservation;

import garbagetown.domain.service.userdetails.ReservationUserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by garbagetown on 10/27/15.
 */
@Controller
@RequestMapping(value = "reservations")
public class ManageReservationController {

    @Inject
    ManageReservationHelper manageReservationHelper;

    @RequestMapping(value="me", method = RequestMethod.GET)
    public String list(@AuthenticationPrincipal ReservationUserDetails userDetails ,Model model) {
        List<ReserveRowOutput> rows = manageReservationHelper.list(userDetails);

        model.addAttribute("rows", rows);
        return "managereservation/list";
    }
}
