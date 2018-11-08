package system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import system.service.FiasService;

import java.util.List;

@Controller
//@Api(tags = "Admin", description = " ")
@ApiIgnore
public class AdminController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @ApiIgnore
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin() {
        return "admin.html";
    }

    @ApiOperation(value = "Get current local database version")
    @RequestMapping(value = "/rest/getCurrentVersion", method = RequestMethod.GET)
    @ResponseBody
    public String[] getCurrentVersion(){
        return new String[]{fiasService.getCurrentVersion()};
    }

    @ApiOperation(value = "Get last global database version")
    @RequestMapping(value = "/rest/getLastVersion", method = RequestMethod.GET)
    @ResponseBody
    public String[] getLastVersion(){
        return new String[]{fiasService.getLastVersion()};
    }

    @ApiOperation(value = "Get need update versions")
    @RequestMapping(value = "/rest/getNewVersions", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getNewVersions(){
        return fiasService.getListOfNewVersions();
    }

    @ApiOperation(value = "Install last complete database")
    @RequestMapping(value = "/rest/installComplete", method = RequestMethod.GET)
    @ResponseBody
    public boolean installComplete(){
        return fiasService.installComplete();
    }

    @ApiOperation(value = "Install one database update")
    @RequestMapping(value = "/rest/installOneUpdate", method = RequestMethod.GET)
    @ResponseBody
    public boolean installOneUpdate(){
        return fiasService.installOneUpdate();
    }

    @ApiOperation(value = "Install all database updates")
    @RequestMapping(value = "/rest/installUpdates", method = RequestMethod.GET)
    @ResponseBody
    public boolean installUpdates(){
        return fiasService.installUpdates();
    }
}
