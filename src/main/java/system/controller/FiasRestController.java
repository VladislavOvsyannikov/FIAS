package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import system.model.House;
import system.model.Room;
import system.model.Stead;
import system.service.FiasService;
import system.model.Object;

import java.util.List;

@RestController
public class FiasRestController {

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @RequestMapping(value = "/getObjectsListByGuid", method = RequestMethod.POST)
    public List<Object> getObjectsListByGuid(@RequestBody String guid){
        return fiasService.getObjectsListByGuid(guid);
    }

    @RequestMapping(value = "/getSteadsListByGuid", method = RequestMethod.POST)
    public List<Stead> getSteadsListByGuid(@RequestBody String guid){
        return fiasService.getSteadsListByGuid(guid);
    }

    @RequestMapping(value = "/getHousesListByGuid", method = RequestMethod.POST)
    public List<House> getHousesListByGuid(@RequestBody String guid){
        return fiasService.getHousesListByGuid(guid);
    }

    @RequestMapping(value = "/getRoomsListByGuid", method = RequestMethod.POST)
    public List<Room> getRoomsListByGuid(@RequestBody String guid){
        return fiasService.getRoomsListByGuid(guid);
    }

    @RequestMapping(value = "/getCurrentVersion", method = RequestMethod.POST)
    public String getCurrentVersion(){
        return fiasService.getCurrentVersion();
    }

    @RequestMapping(value = "/getLastVersion", method = RequestMethod.POST)
    public String getLastVersion(){
        return fiasService.getLastVersion();
    }

    @RequestMapping(value = "/getNewVersions", method = RequestMethod.POST)
    public List<String> getNewVersions(){
        return fiasService.getListOfNewVersions();
    }

    @RequestMapping(value = "/installComplete", method = RequestMethod.POST)
    public void installComplete(){
        fiasService.installComplete();
    }

    @RequestMapping(value = "/installOneUpdate", method = RequestMethod.POST)
    public void installOneUpdate(){
        fiasService.installOneUpdate();
    }

    @RequestMapping(value = "/installUpdates", method = RequestMethod.POST)
    public void installUpdates(){
        fiasService.installUpdates();
    }
}
