package garbagetown.app.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by garbagetown on 10/25/15.
 */
@Slf4j
@Controller
@RequestMapping(value = "login")
public class LoginController {

    @RequestMapping
    public String index() {
        return "login/form";
    }
}
