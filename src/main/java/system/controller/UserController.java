package system.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
@Secured("ROLE_USER")
@Api(tags = "User", description = " ")
public class UserController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @ApiOperation(value = "Get information about objects satisfying parameters (guid, postalcode)")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AddrObject[].class)})
    @RequestMapping(value = "/rest/searchObjectsByParameters", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> searchObjectsByParameters(
            @ApiParam(name = "guid", value = "guid for search", example = "75ce0230-1799-4d47-814d-64f55fb0db70")
            @RequestParam(required = false) String guid,
            @ApiParam(name = "postalcode", value = "postalcode for search", example = "676758")
            @RequestParam(required = false) String postalcode){
        return fiasService.searchObjectsByParameters(guid, postalcode);
    }

    @ApiOperation(value = "Get information about object by guid")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AddrObject.class)})
    @RequestMapping(value = "/rest/searchObjectByGuid", method = RequestMethod.GET)
    @ResponseBody
    public Object searchObjectByGuid(
            @ApiParam(name = "guid", value = "guid for search", required = true, example = "75ce0230-1799-4d47-814d-64f55fb0db70")
            @RequestParam String guid){
        return fiasService.searchObjectByGuid(guid);
    }

    @ApiOperation(value = "Get full address by guid")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = String[].class)})
    @RequestMapping(value = "/rest/getFullAddressByGuid", method = RequestMethod.GET)
    @ResponseBody
    public String[] getFullAddress(
            @ApiParam(name = "guid", value = "guid for search", required = true, example = "75ce0230-1799-4d47-814d-64f55fb0db70")
            @RequestParam String guid){
        return new String[]{fiasService.getFullAddress(guid)};
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/searchObjects")
    @ResponseBody
    public List<Object> searchObjects(@RequestBody LinkedHashMap<String, String> parameters){
        return fiasService.searchObjects(parameters);
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getAddrObjectsByParentGuid")
    @ResponseBody
    public List<AddrObject> getAddrObjectsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getAddrObjectsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getSteadsByParentGuid")
    @ResponseBody
    public List<Stead> getSteadsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getSteadsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getHousesByParentGuid")
    @ResponseBody
    public List<House> getHousesByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getHousesByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getRoomsListByParentGuid")
    @ResponseBody
    public List<Room> getRoomsListByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getRoomsListByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @ApiIgnore
    @RequestMapping(value = "/rest/getCurrentUserInfo")
    @ResponseBody
    public List<String> getCurrentUserInfo(){
        return fiasService.getCurrentUserInfo();
    }

    @ApiIgnore
    @RequestMapping(value = "/user")
    public String user() {
        return "user.html";
    }

    @ApiIgnore
    @RequestMapping(value = "/template.html")
    public String template() {
        return "template.html";
    }
}


