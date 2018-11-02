package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import system.model.*;
import system.model.Object;
import system.service.FiasService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class FiasRestController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @RequestMapping(value = "/rest/getObjectsByParentGuid")
    public List<Object> getObjectsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getObjectsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/rest/getSteadsByParentGuid")
    public List<Stead> getSteadsByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getSteadsByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/rest/getHousesByParentGuid")
    public List<House> getHousesByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getHousesByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/rest/getRoomsListByParentGuid")
    public List<Room> getRoomsListByParentGuid(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.getRoomsListByParentGuid(params.get("guid"),
                Boolean.parseBoolean(params.getOrDefault("actual", "true")));
    }

    @RequestMapping(value = "/rest/getCurrentVersion")
    public String[] getCurrentVersion(){
        return new String[]{fiasService.getCurrentVersion()};
    }

    @RequestMapping(value = "/rest/getLastVersion")
    public String[] getLastVersion(){
        return new String[]{fiasService.getLastVersion()};
    }

    @RequestMapping(value = "/rest/getNewVersions")
    public List<String> getNewVersions(){
        return fiasService.getListOfNewVersions();
    }

    @RequestMapping(value = "/rest/installComplete")
    public boolean installComplete(){
        return fiasService.installComplete();
    }

    @RequestMapping(value = "/rest/installOneUpdate")
    public boolean installOneUpdate(){
        return fiasService.installOneUpdate();
    }

    @RequestMapping(value = "/rest/installUpdates")
    public boolean installUpdates(){
        return fiasService.installUpdates();
    }

    @RequestMapping(value = "/rest/searchObjects")
    public List<java.lang.Object> searchObjects(@RequestBody LinkedHashMap<String, String> params){
        return fiasService.searchObjects(params);
    }

    @RequestMapping(value = "/rest/getUserName")
    public String[] getUserName(){
        return new String[]{fiasService.getUserName()};
    }

    @RequestMapping(value = "/submitRegistration")
    public boolean submitRegistration(@RequestBody User user){
        return fiasService.submitRegistration(user);
    }
}
