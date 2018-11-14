package system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import system.model.User;
import system.service.FiasService;

import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
@Api(tags = "Admin", description = " ")
public class AdminController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @ApiOperation(value = "Get current local database version")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = String[].class)})
    @RequestMapping(value = "/rest/getCurrentVersion", method = RequestMethod.GET)
    @ResponseBody
    public String[] getCurrentVersion(){
        return new String[]{fiasService.getCurrentVersion()};
    }

    @ApiOperation(value = "Get last global database version")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = String[].class)})
    @RequestMapping(value = "/rest/getLastVersion", method = RequestMethod.GET)
    @ResponseBody
    public String[] getLastVersion(){
        return new String[]{fiasService.getLastVersion()};
    }

    @ApiOperation(value = "Get need update versions")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = String[].class)})
    @RequestMapping(value = "/rest/getNewVersions", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getNewVersions(){
        return fiasService.getListOfNewVersions();
    }

    @ApiOperation(value = "Install last complete database")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Boolean.class)})
    @RequestMapping(value = "/rest/installComplete", method = RequestMethod.GET)
    @ResponseBody
    public boolean installComplete(){
        return fiasService.installComplete();
    }

    @ApiOperation(value = "Install one database update")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Boolean.class)})
    @RequestMapping(value = "/rest/installOneUpdate", method = RequestMethod.GET)
    @ResponseBody
    public boolean installOneUpdate(){
        return fiasService.installOneUpdate();
    }

    @ApiOperation(value = "Install all database updates")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = Boolean.class)})
    @RequestMapping(value = "/rest/installUpdates", method = RequestMethod.GET)
    @ResponseBody
    public boolean installUpdates(){
        return fiasService.installUpdates();
    }

    @ApiIgnore @RequestMapping(value = "/admin")
    public String admin() {
        return "admin.html";
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/signUp")
    @ResponseBody
    public boolean signUp(@RequestBody User user){
        return fiasService.signUp(user);
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getAllUsers")
    @ResponseBody
    public List<User> getAllUsersWithoutPasswords(){
        return fiasService.getAllUsersWithoutPasswords();
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/deleteUser")
    @ResponseBody
    public void deleteUser(@RequestBody User user){
        fiasService.deleteUser(user);
    }
    @ApiIgnore
    @RequestMapping(value = "/rest/blockUser")
    @ResponseBody
    public void blockUser(@RequestBody User user){
        fiasService.blockUser(user);
    }
}
