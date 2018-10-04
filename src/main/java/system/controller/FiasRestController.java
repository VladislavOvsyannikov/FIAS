package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(value = "/getObjectsStartList", method = RequestMethod.GET)
    public List<Object> getObjectsStartList(){
        return fiasService.getObjectsStartList();
    }

    @RequestMapping(value = "/getObjectsListByGuid", method = RequestMethod.POST)
    public List<Object> getObjectsListByGuid(@RequestBody String guid){
        List<Object> list = fiasService.getObjectsListByGuid(guid);
        return list;
    }
}
