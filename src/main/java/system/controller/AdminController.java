package system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import system.dto.UserDto;
import system.service.FiasModuleStatus;
import system.service.FiasService;

import java.util.List;

import static java.util.Objects.nonNull;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/fias")
@RequiredArgsConstructor
@Api(tags = "Admin", description = " ")
public class AdminController {

    private final FiasService fiasService;

    @ResponseBody
    @GetMapping(value = "/module-status")
    @ApiOperation("Статус модуля ФИАС")
    ResponseEntity<FiasModuleStatus> getFiasModuleStatus() {
        return new ResponseEntity<>(fiasService.getFiasModuleStatus(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/current-version")
    @ApiOperation("Текущая версия локальной базы ФИАС")
    ResponseEntity<String> getCurrentVersion() {
        String version = fiasService.getCurrentVersion();
        return nonNull(version) ? new ResponseEntity<>(version, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/last-version")
    @ApiOperation("Последняя версия базы ФИАС")
    ResponseEntity<String> getLastVersion() {
        String version = fiasService.getLastVersion();
        return nonNull(version) ? new ResponseEntity<>(version, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/new-versions")
    @ApiOperation("Необходимые версии обновления")
    ResponseEntity<List<String>> getNewVersions() {
        List<String> versions = fiasService.getListOfNewVersions();
        return nonNull(versions) ? new ResponseEntity<>(versions, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Boolean> signUp(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "password") String password) {
        return fiasService.signUp(name, password) ? new ResponseEntity<>(true, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAllUsersWithoutPasswords() {
        List<UserDto> users = fiasService.getAllUsersWithoutPasswords();
        return users.isEmpty() ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiIgnore
    @ResponseBody
    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestParam(value = "id") Integer id) {
        fiasService.deleteUser(id);
    }

    @ApiIgnore
    @ResponseBody
    @PostMapping(value = "/user")
    public void blockUser(@RequestParam(value = "id") Integer id) {
        fiasService.blockUser(id);
    }

    @ApiIgnore
    @ResponseBody
    @GetMapping(value = "/last-log")
    public ResponseEntity<String> lastLog() {
        return new ResponseEntity<>(fiasService.lastLog(), HttpStatus.OK);
    }

    @ApiIgnore
    @RequestMapping(value = "/admin")
    public String admin() {
        return "admin.html";
    }
}
