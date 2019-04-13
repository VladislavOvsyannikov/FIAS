package fias.controller;

import fias.service.FiasModuleStatus;
import fias.service.FiasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import security.user.UserDto;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/fias")
@RequiredArgsConstructor
@Api(tags = "Администрирование", description = " ")
public class AdminController {

    private final FiasService fiasService;

    @ResponseBody
    @GetMapping(value = "/module-status")
    @ApiOperation("Статус модуля ФИАС")
    FiasModuleStatus getFiasModuleStatus() {
        return fiasService.getFiasModuleStatus();
    }

    @ResponseBody
    @GetMapping(value = "/current-version")
    @ApiOperation("Текущая версия локальной базы ФИАС")
    String getCurrentVersion() {
        return fiasService.getCurrentVersion();
    }

    @ResponseBody
    @GetMapping(value = "/last-version")
    @ApiOperation("Последняя версия базы ФИАС")
    String getLastVersion() {
        return fiasService.getLastVersion();
    }

    @ResponseBody
    @GetMapping(value = "/new-versions")
    @ApiOperation("Необходимые версии обновления")
    List<String> getNewVersions() {
        return fiasService.getListOfNewVersions();
    }

    @ResponseBody
    @GetMapping(value = "/complete")
    @ApiOperation("Установка полной базы ФИАС")
    void installComplete() {
        fiasService.installComplete();
    }

    @ResponseBody
    @GetMapping(value = "/update")
    @ApiOperation("Установка одного обновления базы ФИАС")
    void installOneUpdate() {
        fiasService.installUpdates(true);
    }

    @ResponseBody
    @GetMapping(value = "/updates")
    @ApiOperation("Установка всех обновлений базы ФИАС")
    void installUpdates() {
        fiasService.installUpdates(false);
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping(value = "/sign-up")
    Boolean signUp(@RequestParam(value = "name") String name,
                   @RequestParam(value = "password") String password) {
        return fiasService.signUp(name, password);
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping(value = "/users")
    List<UserDto> getAllUsersWithoutPasswords() {
        return fiasService.getAllUsersWithoutPasswords();
    }

    @ApiIgnore
    @ResponseBody
    @DeleteMapping(value = "/user")
    void deleteUser(@RequestParam(value = "id") Integer id) {
        fiasService.deleteUser(id);
    }

    @ApiIgnore
    @ResponseBody
    @PostMapping(value = "/user")
    void blockUser(@RequestParam(value = "id") Integer id) {
        fiasService.blockUser(id);
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping(value = "/last-log")
    String lastLog() {
        return fiasService.lastLog();
    }

    @ApiIgnore
    @RequestMapping(value = "/admin")
    String admin() {
        return "admin.html";
    }
}
