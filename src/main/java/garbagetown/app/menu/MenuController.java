package garbagetown.app.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by garbagetown on 10/6/15.
 */
@Slf4j
@Controller
@RequestMapping("/")
public class MenuController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String init() {
        return "menu/menu";
    }
}
