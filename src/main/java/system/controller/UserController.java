package system.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import system.model.House;
import system.model.Object;
import system.model.Room;
import system.model.Stead;
import system.service.FiasService;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
@Api(tags = "User", description = " ")
public class UserController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @ApiIgnore
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return "user.html";
    }

//    @ApiOperation(value = "Get current user information")
    @RequestMapping(value = "/rest/getCurrentUserInfo", method = RequestMethod.GET)
    @ApiIgnore
    @ResponseBody
    public List<String> getCurrentUserInfo(){
        return fiasService.getCurrentUserInfo();
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getObjectsByParentGuid", method = RequestMethod.POST)
    @ResponseBody
    public List<Object> getObjectsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getObjectsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getSteadsByParentGuid", method = RequestMethod.POST)
    @ResponseBody
    public List<Stead> getSteadsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getSteadsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getHousesByParentGuid", method = RequestMethod.POST)
    @ResponseBody
    public List<House> getHousesByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getHousesByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getRoomsListByParentGuid", method = RequestMethod.POST)
    @ResponseBody
    public List<Room> getRoomsListByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getRoomsListByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }



    @ApiOperation(value = "Get information about objects satisfying parameters (guid, postalcode...)")
    @ApiImplicitParams({@ApiImplicitParam(name = "parameters", dataType = "Parameters",
            value = "parameters for search", required = true)})
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = system.model.Object.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @RequestMapping(value = "/rest/searchObjects", method = RequestMethod.POST)
    @ResponseBody
    public List<java.lang.Object> searchObjects(@RequestBody LinkedHashMap<String, String> parameters){
        return fiasService.searchObjects(parameters);
    }
}


