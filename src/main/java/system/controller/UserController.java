package system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import system.dto.AddrObjectDto;
import system.dto.HouseDto;
import system.dto.RoomDto;
import system.dto.SteadDto;
import system.service.CustomPair;
import system.service.FiasService;
import system.service.NameSearchType;
import system.service.ParameterSearchType;

import java.util.List;

import static java.util.Objects.nonNull;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/fias")
@RequiredArgsConstructor
@Api(tags = "User", description = " ")
public class UserController {

    private final FiasService fiasService;

    @ResponseBody
    @GetMapping(value = "/objects-parameters")
    @ApiOperation("Объекты ФИАС по параметрам")
    ResponseEntity<List<Object>> searchObjectsByParameters(
            @RequestParam(value = "guid", required = false) String guid,
            @RequestParam(value = "postalcode", required = false) String postalcode,
            @RequestParam(value = "searchType", required = false) ParameterSearchType type,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        List<Object> objects = fiasService.searchObjectsByParameters(guid, postalcode, type, isActual);
        return (!objects.isEmpty()) ? new ResponseEntity<>(objects, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/objects-parameters/types")
    @ApiOperation("Типы для поиска объектов ФИАС по параметрам")
    ResponseEntity<ParameterSearchType[]> getParameterSearchTypes() {
        return new ResponseEntity<>(ParameterSearchType.values(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/addr-objects-name")
    @ApiOperation("Адресные объекты по имени и типу")
    ResponseEntity<List<AddrObjectDto>> getAddrObjectsByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "type", required = false) NameSearchType type,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        List<AddrObjectDto> addrObjects = fiasService.getAddrObjectsByName(name, type, isActual);
        return (!addrObjects.isEmpty()) ? new ResponseEntity<>(addrObjects, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/addr-objects-name/types")
    @ApiOperation("Типы адресных объектов для поиска по имени")
    ResponseEntity<NameSearchType[]> getNameSearchTypes() {
        return new ResponseEntity<>(NameSearchType.values(), HttpStatus.OK);
    }

    @GetMapping(value = "/old-addr-objects/{guid}")
    @ApiOperation("Старые и актуальный адресные объекты")
    ResponseEntity<CustomPair> searchOldAddrObjectsByGuid(@PathVariable(value = "guid") String guid) {
        CustomPair pair = fiasService.searchOldAddrObjectsByGuid(guid);
        return nonNull(pair) ? new ResponseEntity<>(pair, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/initial-addr-objects")
    @ApiOperation("Начальные адресные объекты")
    ResponseEntity<List<AddrObjectDto>> getAddrObjectsStartList() {
        List<AddrObjectDto> addrObjects = fiasService.getAddrObjectsStartList();
        return (!addrObjects.isEmpty()) ? new ResponseEntity<>(addrObjects, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/addr-objects-parent")
    @ApiOperation("Дочерние адресные объекты по родительскому GUID")
    ResponseEntity<List<AddrObjectDto>> getAddrObjectsByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        List<AddrObjectDto> addrObjects = fiasService.getAddrObjectsByParentGuid(guid, isActual);
        return (!addrObjects.isEmpty()) ? new ResponseEntity<>(addrObjects, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/steads-parent")
    @ApiOperation("Дочерние участки по родительскому GUID")
    ResponseEntity<List<SteadDto>> getSteadsByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        List<SteadDto> steads = fiasService.getSteadsByParentGuid(guid, isActual);
        return (!steads.isEmpty()) ? new ResponseEntity<>(steads, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/houses-parent")
    @ApiOperation("Дочерние дома по родительскому GUID")
    ResponseEntity<List<HouseDto>> getHousesByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        List<HouseDto> houses = fiasService.getHousesByParentGuid(guid, isActual);
        return (!houses.isEmpty()) ? new ResponseEntity<>(houses, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/rooms-parent")
    @ApiOperation("Дочерние квартиры по родительскому GUID")
    ResponseEntity<List<RoomDto>> getRoomsListByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        List<RoomDto> rooms = fiasService.getRoomsListByParentGuid(guid, isActual);
        return (!rooms.isEmpty()) ? new ResponseEntity<>(rooms, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/object/{guid}")
    @ApiOperation("Объект ФИАС")
    ResponseEntity<Object> searchObjectByGuid(@PathVariable(value = "guid") String guid) {
        Object object = fiasService.searchObjectByGuid(guid);
        return (nonNull(object)) ? new ResponseEntity<>(object, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping(value = "/full-address/{guid}")
    @ApiOperation("Полный адрес объекта")
    ResponseEntity<String> getFullAddressByGuid(@PathVariable(value = "guid") String guid) {
        String address = fiasService.getFullAddress(guid);
        return (nonNull(address)) ? new ResponseEntity<>(address, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @ApiIgnore
//    @RequestMapping(value = "/rest/getCurrentUserInfo")
//    @ResponseBody
//    public List<String> getCurrentUserInfo() {
//        return fiasService.getCurrentUserInfo();
//    }

    @ApiIgnore
    @GetMapping(value = "/user")
    public String user() {
        return "user.html";
    }

    @ApiIgnore
    @GetMapping(value = "/template.html")
    public String template() {
        return "template.html";
    }

    @ApiIgnore
    @GetMapping(value = "/templateNameSearch.html")
    public String templateNameSearch() {
        return "templateNameSearch.html";
    }
}


