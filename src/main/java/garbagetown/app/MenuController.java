package garbagetown.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by garbagetown on 10/6/15.
 */
@Controller
@RequestMapping("/")
public class MenuController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "menu/menu";
    }
}
