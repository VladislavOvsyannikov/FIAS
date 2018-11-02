package system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FiasController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "admin.html";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return "user.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login.html";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration.html";
    }

    @RequestMapping(value = "/template.html", method = RequestMethod.GET)
    public String template() {
        return "template.html";
    }
}
