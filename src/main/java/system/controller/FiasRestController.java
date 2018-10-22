package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.model.primary.House;
import system.model.primary.Room;
import system.model.primary.Stead;
import system.service.FiasService;
import system.model.primary.Object;

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
    public List<Object> getObjectsByParentGuid(@RequestBody String guid){
        return fiasService.getObjectsByParentGuid(guid);
    }

    @RequestMapping(value = "/getSteadsByParentGuid")
    public List<Stead> getSteadsByParentGuid(@RequestBody String guid){
        return fiasService.getSteadsByParentGuid(guid);
    }

    @RequestMapping(value = "/getHousesByParentGuid")
    public List<House> getHousesByParentGuid(@RequestBody String guid){
        return fiasService.getHousesByParentGuid(guid);
    }

    @RequestMapping(value = "/getRoomsListByParentGuid")
    public List<Room> getRoomsListByParentGuid(@RequestBody String guid){
        return fiasService.getRoomsListByParentGuid(guid);
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
