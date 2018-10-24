package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.Object;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class ObjectDao extends GenericDao<Object>{

    private AddressObjectTypeDao addressObjectTypeDao;

    @Autowired
    public void setAddressObjectTypeDao(AddressObjectTypeDao addressObjectTypeDao) {
        this.addressObjectTypeDao = addressObjectTypeDao;
    }


    public List<Object> getObjectsByParentGuid(String guid, boolean isActual) {
        if (guid.equals(" ")) return getObjectsStartList();
        else {
            String queryPart = isActual? " and livestatus=1": "";
            List<Object> objects = getEntities(
                    "select * from object where parentguid=\""+guid+"\""+queryPart, Object.class);
            if (!isActual) objects = getObjectsWithMaxEnddate(objects);
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

    public List<Object> getObjectsByParams(LinkedHashMap<String, String> params, boolean isActual){
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("aoguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        if (isActual) strings.add("livestatus=1");
        String queryPart = String.join(" and ", strings);
        List<Object> objects = getEntities("select * from object where "+queryPart, Object.class);
        if (objects != null && objects.size() > 0) {
            if (!isActual) objects = getObjectsWithMaxEnddate(objects);
            for (Object object:objects) object.setFullAddress(getFullAddress(object));
            return objects;
        }
        return null;
    }

    private List<Object> getObjectsWithMaxEnddate(List<Object> objects) {
        if (objects.size() <= 1) return objects;
        objects.sort(Comparator.comparing(Object::getAOGUID));
        List<Object> res = new ArrayList<>();
        Object maxObject = objects.get(0);
        Object currentObject;
        for (int i=1; i<objects.size(); i++){
            currentObject = objects.get(i);
            if (maxObject.getAOGUID().equals(currentObject.getAOGUID())){
                if (maxObject.getENDDATE() < currentObject.getENDDATE()) maxObject = currentObject;
            }else{
                res.add(maxObject);
                maxObject = currentObject;
            }
            if (i == objects.size()-1) res.add(maxObject);
        }
        return res;
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

    private Object getObjectByGuid(String guid){
        List<Object> objects = getEntities("select * from object where aoguid=\""+guid+"\"", Object.class);
        if (objects.size() == 0) return null;
        objects = getObjectsWithMaxEnddate(objects);
        return objects.get(0);
    }

    private String getFullType(Object object){
        return addressObjectTypeDao.getFullName(object.getAOLEVEL(), object.getSHORTNAME());
    }
}
