package security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import security.user.UserDto;
import security.user.UserService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@RestController
@RequestMapping("/fias")
@RequiredArgsConstructor
public class SecutiryController {

    private final UserService userService;

    @GetMapping(value = "/sign-in")
    Boolean signIn(@RequestParam(value = "name") String name,
                   @RequestParam(value = "password") String password) {
        return userService.signIn(name, password);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/sign-up")
    Boolean signUp(@RequestParam(value = "name") String name,
                   @RequestParam(value = "password") String password) {
        return userService.signUp(name, password);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/users")
    List<UserDto> getAllUsersWithoutPasswords() {
        return userService.getAllUsersWithoutPasswords();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/user")
    void deleteUser(@RequestParam(value = "id") Integer id) {
        userService.deleteUser(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/user")
    void blockUser(@RequestParam(value = "id") Integer id) {
        userService.blockUser(id);
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "/user-info")
    List<String> getCurrentUserInfo() {
        return userService.getCurrentUserInfo();
    }
}
