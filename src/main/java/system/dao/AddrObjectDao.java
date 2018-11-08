package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.AddrObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class AddrObjectDao extends GenericDao<AddrObject>{

    private AddressObjectTypeDao addressObjectTypeDao;

    @Autowired
    public void setAddressObjectTypeDao(AddressObjectTypeDao addressObjectTypeDao) {
        this.addressObjectTypeDao = addressObjectTypeDao;
    }


    public List<AddrObject> getAddrObjectsByParentGuid(String guid, boolean isActual) {
        if (guid.equals(" ")) return getAddrObjectsStartList();
        else {
            String queryPart = isActual? " and livestatus=1": "";
            List<AddrObject> addrObjects = getEntities(
                    "select * from object where parentguid=\""+guid+"\""+queryPart, AddrObject.class);
            if (!isActual) addrObjects = getAddrObjectsWithMaxEnddate(addrObjects);
            for (AddrObject addrObject : addrObjects) addrObject.setSHORTNAME(getFullType(addrObject));
            return addrObjects;
        }
    }

    private List<AddrObject> getAddrObjectsStartList() {
        List<AddrObject> addrObjects = getEntities(
                "select * from object where parentguid is null and livestatus=1", AddrObject.class);
        for (AddrObject addrObject : addrObjects){
            addrObject.setSHORTNAME(getFullType(addrObject));
            if (addrObject.getFORMALNAME().contains("Чувашская")){
                addrObject.setSHORTNAME("республика");
                addrObject.setFORMALNAME("Чувашская");
            }
        }
        return addrObjects;
    }

    public List<AddrObject> getAddrObjectsByParams(LinkedHashMap<String, String> params, boolean isActual){
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("aoguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        if (isActual) strings.add("livestatus=1");
        String queryPart = String.join(" and ", strings);
        List<AddrObject> addrObjects = getEntities("select * from object where "+queryPart, AddrObject.class);
        if (addrObjects != null && addrObjects.size() > 0) {
            if (!isActual) addrObjects = getAddrObjectsWithMaxEnddate(addrObjects);
            for (AddrObject addrObject : addrObjects) addrObject.setFullAddress(getFullAddress(addrObject));
            return addrObjects;
        }
        return null;
    }

    private List<AddrObject> getAddrObjectsWithMaxEnddate(List<AddrObject> addrObjects) {
        if (addrObjects.size() <= 1) return addrObjects;
        addrObjects.sort(Comparator.comparing(AddrObject::getAOGUID));
        List<AddrObject> res = new ArrayList<>();
        AddrObject maxAddrObject = addrObjects.get(0);
        AddrObject currentAddrObject;
        for (int i = 1; i< addrObjects.size(); i++){
            currentAddrObject = addrObjects.get(i);
            if (maxAddrObject.getAOGUID().equals(currentAddrObject.getAOGUID())){
                if (maxAddrObject.getENDDATE() < currentAddrObject.getENDDATE()) maxAddrObject = currentAddrObject;
            }else{
                res.add(maxAddrObject);
                maxAddrObject = currentAddrObject;
            }
            if (i == addrObjects.size()-1) res.add(maxAddrObject);
        }
        return res;
    }

    public String getFullAddress(String guid){
        AddrObject addrObject = getAddrObjectByGuid(guid);
        String fullAddress = getFullType(addrObject) + " " + addrObject.getFORMALNAME();
        while (addrObject.getPARENTGUID() != null){
            addrObject = getAddrObjectByGuid(addrObject.getPARENTGUID());
            fullAddress = getFullType(addrObject) + " " + addrObject.getFORMALNAME() + ", " + fullAddress;
        }
        return fullAddress;
    }

    private String getFullAddress(AddrObject addrObject){
        String fullAddress = getFullType(addrObject) + " " + addrObject.getFORMALNAME();
        while (addrObject.getPARENTGUID() != null){
            addrObject = getAddrObjectByGuid(addrObject.getPARENTGUID());
            fullAddress = getFullType(addrObject) + " " + addrObject.getFORMALNAME() + ", " + fullAddress;
        }
        return fullAddress;
    }

    private AddrObject getAddrObjectByGuid(String guid){
        List<AddrObject> addrObjects = getEntities("select * from object where aoguid=\""+guid+"\"", AddrObject.class);
        if (addrObjects.size() == 0) return null;
        addrObjects = getAddrObjectsWithMaxEnddate(addrObjects);
        return addrObjects.get(0);
    }

    private String getFullType(AddrObject addrObject){
        return addressObjectTypeDao.getFullName(addrObject.getAOLEVEL(), addrObject.getSHORTNAME());
    }
}
