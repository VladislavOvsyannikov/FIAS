package system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
import system.dto.NormativeDocumentDto;
import system.dto.RoomDto;
import system.dto.SteadDto;
import system.service.CustomPair;
import system.service.FiasService;
import system.service.NameSearchType;
import system.service.ParameterSearchType;

import java.util.List;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/fias")
@RequiredArgsConstructor
@Api(tags = "Сервис", description = " ")
public class UserController {

    private final FiasService fiasService;

    @ResponseBody
    @GetMapping(value = "/objects-parameters")
    @ApiOperation("Объекты ФИАС по параметрам")
    List<Object> searchObjectsByParameters(
            @RequestParam(value = "guid", required = false) String guid,
            @RequestParam(value = "postalcode", required = false) String postalcode,
            @RequestParam(value = "cadnum", required = false) String cadnum,
            @RequestParam(value = "okato", required = false) String okato,
            @RequestParam(value = "oktmo", required = false) String oktmo,
            @RequestParam(value = "fl", required = false) String fl,
            @RequestParam(value = "ul", required = false) String ul,
            @RequestParam(value = "searchTypes", required = false) List<ParameterSearchType> types,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        return fiasService.searchObjectsByParameters(guid, postalcode, cadnum, okato, oktmo, fl, ul, types, isActual);
    }

    @ResponseBody
    @GetMapping(value = "/objects-parameters/types")
    @ApiOperation("Типы для поиска объектов ФИАС по параметрам")
    ParameterSearchType[] getParameterSearchTypes() {
        return ParameterSearchType.values();
    }

    @ResponseBody
    @GetMapping(value = "/addr-objects-name")
    @ApiOperation("Адресные объекты по имени и типу")
    List<AddrObjectDto> getAddrObjectsByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "type", required = false) NameSearchType type,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        return fiasService.getAddrObjectsByName(name, type, isActual);
    }

    @ResponseBody
    @GetMapping(value = "/addr-objects-name/types")
    @ApiOperation("Типы адресных объектов для поиска по имени")
    NameSearchType[] getNameSearchTypes() {
        return NameSearchType.values();
    }

    @GetMapping(value = "/old-addr-objects/{guid}")
    @ApiOperation("Старые и актуальный адресные объекты")
    CustomPair searchOldAddrObjectsByGuid(@PathVariable(value = "guid") String guid) {
        return fiasService.searchOldAddrObjectsByGuid(guid);
    }

    @ResponseBody
    @GetMapping(value = "/initial-addr-objects")
    @ApiOperation("Начальные адресные объекты")
    List<AddrObjectDto> getAddrObjectsStartList() {
        return fiasService.getAddrObjectsStartList();
    }

    @ResponseBody
    @GetMapping(value = "/addr-objects-parent")
    @ApiOperation("Дочерние адресные объекты по родительскому GUID")
    List<AddrObjectDto> getAddrObjectsByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        return (guid.equals("")) ? fiasService.getAddrObjectsStartList() :
                fiasService.getAddrObjectsByParentGuid(guid, isActual);
    }

    @ResponseBody
    @GetMapping(value = "/steads-parent")
    @ApiOperation("Дочерние участки по родительскому GUID")
    List<SteadDto> getSteadsByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        return fiasService.getSteadsByParentGuid(guid, isActual);
    }

    @ResponseBody
    @GetMapping(value = "/houses-parent")
    @ApiOperation("Дочерние дома по родительскому GUID")
    List<HouseDto> getHousesByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {
        return fiasService.getHousesByParentGuid(guid, isActual);
    }

    @ResponseBody
    @GetMapping(value = "/rooms-parent")
    @ApiOperation("Дочерние квартиры по родительскому GUID")
    List<RoomDto> getRoomsListByParentGuid(
            @RequestParam(value = "guid") String guid,
            @RequestParam(value = "isActual", required = false) Boolean isActual) {

        return fiasService.getRoomsListByParentGuid(guid, isActual);
    }

    @ResponseBody
    @GetMapping(value = "/object/{guid}")
    @ApiOperation("Объект ФИАС")
    Object searchObjectByGuid(@PathVariable(value = "guid") String guid) {
        return fiasService.searchObjectByGuid(guid);
    }

    @ResponseBody
    @GetMapping(value = "/full-address/{guid}")
    @ApiOperation("Полный адрес объекта")
    String[] getFullAddressByGuid(@PathVariable(value = "guid") String guid) {
        return new String[]{fiasService.getFullAddress(guid)};
    }

    @ResponseBody
    @GetMapping(value = "/normative-document/{id}")
    @ApiOperation("Нормативный документ")
    NormativeDocumentDto getNormativeDocument(@PathVariable(value = "id") String id) {
        return fiasService.getNormativeDocument(id);
    }

    @ApiIgnore
    @ResponseBody
    @RequestMapping(value = "/user-info")
    public List<String> getCurrentUserInfo() {
        return fiasService.getCurrentUserInfo();
    }

    @ApiIgnore
    @GetMapping(value = "/user")
    String user() {
        return "user.html";
    }

    @ApiIgnore
    @GetMapping(value = "/template.html")
    String template() {
        return "template.html";
    }

    @ApiIgnore
    @GetMapping(value = "/templateNameSearch.html")
    String templateNameSearch() {
        return "templateNameSearch.html";
    }
}


