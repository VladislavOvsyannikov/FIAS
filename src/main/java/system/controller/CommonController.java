package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value = "/signin")
    public String signIn() {
        return "signin.html";
    }

    @RequestMapping(value = "/signIn")
    @ResponseBody
    public boolean signIn(@RequestBody User user){
        return fiasService.signIn(user);
    }

    @RequestMapping(value = "/navbar.html")
    public String navbar() {
        return "navbar.html";
    }
}
