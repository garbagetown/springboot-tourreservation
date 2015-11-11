package garbagetown.app.managereservation;

import garbagetown.domain.model.Reserve;
import garbagetown.domain.service.reserve.ReserveService;
import garbagetown.domain.service.userdetails.ReservationUserDetails;
import org.dozer.Mapper;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Inject
    ReserveService service;

    @Inject
    Mapper mapper;

    @RequestMapping(value="me", method = RequestMethod.GET)
    public String list(@AuthenticationPrincipal ReservationUserDetails userDetails ,Model model) {
        List<ReserveRowOutput> rows = manageReservationHelper.list(userDetails);

        model.addAttribute("rows", rows);
        return "managereservation/list";
    }

    @RequestMapping(value = "{reserveNo}", method = RequestMethod.GET)
    public String detailForm(@PathVariable("reserveNo") String reserveNo, Model model) {
        ReservationDetailOutput output = manageReservationHelper.findDetail(reserveNo);

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
}
