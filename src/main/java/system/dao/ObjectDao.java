package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.primary.Object;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class ObjectDao extends GenericDao<Object>{

    private AddressObjectTypeDao addressObjectTypeDao;

    @Autowired
    public void setAddressObjectTypeDao(AddressObjectTypeDao addressObjectTypeDao) {
        this.addressObjectTypeDao = addressObjectTypeDao;
    }


    public List<Object> getObjectsByParentGuid(String guid) {
        if (guid.equals(" ")) return getObjectsStartList();
        else {
            List<Object> objects = getEntities(
                    "select * from object where parentguid=\""+guid+"\" and livestatus=1", Object.class);
            for (Object object:objects) object.setSHORTNAME(getFullType(object));
            return objects;
        }
    }

    private List<Object> getObjectsStartList() {
        List<Object> objects = getEntities(
                "select * from object where parentguid is null and livestatus=1", Object.class);
        for (Object object:objects){
            object.setSHORTNAME(getFullType(object));
            if (object.getFORMALNAME().contains("Чувашская")){
                object.setSHORTNAME("республика");
                object.setFORMALNAME("Чувашская");
            }
        }
        return objects;
    }

    public List<Object> getObjectsByParams(LinkedHashMap<String, String> params){
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("aoguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        String queryPart = String.join(" and ", strings);
        List<Object> objects = getEntities("select * from object where "+queryPart+" and livestatus=1", Object.class);
        if (objects != null && objects.size() > 0) {
            for (Object object:objects) object.setFullAddress(getFullAddress(object));
            return objects;
        }
        return null;
    }

    public String getFullAddress(String guid){
        Object object = getObjectByGuid(guid);
        String fullAddress = getFullType(object) + " " + object.getFORMALNAME();
        while (object.getPARENTGUID() != null){
            object = getObjectByGuid(object.getPARENTGUID());
            fullAddress = getFullType(object) + " " + object.getFORMALNAME() + ", " + fullAddress;
        }
        return fullAddress;
    }

    private String getFullAddress(Object object){
        String fullAddress = getFullType(object) + " " + object.getFORMALNAME();
        while (object.getPARENTGUID() != null){
            object = getObjectByGuid(object.getPARENTGUID());
            fullAddress = getFullType(object) + " " + object.getFORMALNAME() + ", " + fullAddress;
        }
        return fullAddress;
    }

    //переписать для неактуальных
    private Object getObjectByGuid(String guid){
        return getEntity(
                "select * from object where aoguid=\""+guid+"\" and livestatus=1", Object.class);
    }

    private String getFullType(Object object){
        return addressObjectTypeDao.getFullName(object.getAOLEVEL(), object.getSHORTNAME());
    }
}
