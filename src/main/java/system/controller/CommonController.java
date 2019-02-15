package system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import system.service.FiasService;

@Controller
@ApiIgnore
@RequestMapping("/fias")
@RequiredArgsConstructor
public class CommonController {

    private final FiasService fiasService;

    @ResponseBody
    @GetMapping(value = "/sign-in")
    Boolean signIn(@RequestParam(value = "name") String name,
                    @RequestParam(value = "password") String password) {
        return fiasService.signIn(name, password);
    }

    @RequestMapping(value = "/signin")
    String signIn() {
        return "signin.html";
    }

    @RequestMapping(value = "/navbar.html")
    String navbar() {
        return "navbar.html";
    }
}
