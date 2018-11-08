package system.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import system.model.AddrObject;
import system.model.House;
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

    @ApiOperation(value = "Get information about objects satisfying parameters (guid, postalcode)")
    @ApiImplicitParams({@ApiImplicitParam(name = "parameters", dataType = "Parameters",
            value = "parameters for search", required = true)})
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AddrObject[].class)})
    @RequestMapping(value = "/rest/searchObjects", method = RequestMethod.POST)
    @ResponseBody
    public List<Object> searchObjects(@RequestBody LinkedHashMap<String, String> parameters){
        return fiasService.searchObjects(parameters);
    }

    @ApiOperation(value = "Get information about object by guid")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AddrObject.class)})
    @RequestMapping(value = "/rest/searchObjectByGuid", method = RequestMethod.POST)
    @ResponseBody
    public Object searchObjectByGuid(
            @ApiParam(name = "guid", value = "guid for search", required = true)
            @RequestBody String guid){
        return fiasService.searchObjectByGuid(guid);
    }

    @ApiOperation(value = "Get full address by guid")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = String[].class)})
    @RequestMapping(value = "/rest/getFullAddressByGuid", method = RequestMethod.POST)
    @ResponseBody
    public String[] getFullAddress(
            @ApiParam(name = "guid", value = "guid for search", required = true)
            @RequestBody String guid){
        return new String[]{fiasService.getFullAddress(guid)};
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getAddrObjectsByParentGuid", method = RequestMethod.POST)
    @ResponseBody
    public List<AddrObject> getAddrObjectsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getAddrObjectsByParentGuid(params.get("guid"),
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

    @ApiIgnore
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return "user.html";
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getCurrentUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getCurrentUserInfo(){
        return fiasService.getCurrentUserInfo();
    }
}


