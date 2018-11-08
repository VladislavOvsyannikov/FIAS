package system.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import system.model.User;
import system.service.FiasService;

@Controller
@ApiIgnore
public class CommonController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login.html";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration.html";
    }

    @RequestMapping(value = "/submitRegistration", method = RequestMethod.POST)
    @ResponseBody
    public boolean submitRegistration(@RequestBody User user){
        return fiasService.submitRegistration(user);
    }

    @RequestMapping(value = "/template.html", method = RequestMethod.GET)
    public String template() {
        return "template.html";
    }

    @RequestMapping(value = "/navbar.html", method = RequestMethod.GET)
    public String navbar() {
        return "navbar.html";
    }
}
