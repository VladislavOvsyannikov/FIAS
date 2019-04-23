package fias.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@RequestMapping("/fias")
public class CommonController {

    @RequestMapping(value = "/signin")
    String signIn() {
        return "signin.html";
    }

    @RequestMapping(value = "/navbar.html")
    String navbar() {
        return "navbar.html";
    }
}
