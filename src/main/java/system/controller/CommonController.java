package system.controller;

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

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn() {
        return "signin.html";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp() {
        return "signup.html";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public boolean signIn(@RequestBody User user){
        return fiasService.signIn(user);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    @ResponseBody
    public boolean signUp(@RequestBody User user){
        return fiasService.signUp(user);
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
