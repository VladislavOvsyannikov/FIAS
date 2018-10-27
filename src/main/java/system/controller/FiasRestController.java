package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import system.model.House;
import system.model.Room;
import system.model.Stead;
import system.service.FiasService;
import system.model.Object;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class FiasRestController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @RequestMapping(value = "/getObjectsByParentGuid")
    public List<Object> getObjectsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getObjectsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/getSteadsByParentGuid")
    public List<Stead> getSteadsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getSteadsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/getHousesByParentGuid")
    public List<House> getHousesByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getHousesByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/getRoomsListByParentGuid")
    public List<Room> getRoomsListByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getRoomsListByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/getCurrentVersion")
    public String getCurrentVersion(){
        return fiasService.getCurrentVersion();
    }

    @RequestMapping(value = "/getLastVersion")
    public String getLastVersion(){
        return fiasService.getLastVersion();
    }

    @RequestMapping(value = "/getNewVersions")
    public List<String> getNewVersions(){
        return fiasService.getListOfNewVersions();
    }

    @RequestMapping(value = "/installComplete")
    public boolean installComplete(){
        return fiasService.installComplete();
    }

    @RequestMapping(value = "/installOneUpdate")
    public boolean installOneUpdate(){
        return fiasService.installOneUpdate();
    }

    @RequestMapping(value = "/installUpdates")
    public boolean installUpdates(){
        return fiasService.installUpdates();
    }

    @RequestMapping(value = "/searchObjects")
    public List<java.lang.Object> searchObjects(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.searchObjects(params);
    }
}
