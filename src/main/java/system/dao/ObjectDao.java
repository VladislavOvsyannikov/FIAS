package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.Object;

import java.util.List;

@Repository
public class ObjectDao extends GenericDao<Object>{

    private AddressObjectTypeDao addressObjectTypeDao;

    @Autowired
    public void setAddressObjectTypeDao(AddressObjectTypeDao addressObjectTypeDao) {
        this.addressObjectTypeDao = addressObjectTypeDao;
    }


    private List<Object> getObjectsStartList() {
        List<Object> objects = setFullObjectType(getEntities(
                "select * from object where parentguid is null and livestatus=1", Object.class));
        for (Object object:objects){
            if (object.getFORMALNAME().contains("Чувашская")){
                object.setSHORTNAME("республика");
                object.setFORMALNAME("Чувашская");
                break;
            }
        }
        return objects;
    }

    public List<Object> getObjectsListByGuid(String guid) {
        if (guid.equals(" ")){
            return getObjectsStartList();
        }else return setFullObjectType(getEntities(
                "select * from object where parentguid=\""+guid+"\" and livestatus=1", Object.class));
    }

    private List<Object> setFullObjectType(List<Object> objects){
        for (Object object : objects){
            object.setSHORTNAME(addressObjectTypeDao.getFullName(object.getAOLEVEL(), object.getSHORTNAME()));
        }
        return objects;
    }
}
