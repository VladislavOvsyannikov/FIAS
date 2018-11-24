package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.AddrObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class AddrObjectDao extends GenericDao<AddrObject> {

    private AddressObjectTypeDao addressObjectTypeDao;

    @Autowired
    public void setAddressObjectTypeDao(AddressObjectTypeDao addressObjectTypeDao) {
        this.addressObjectTypeDao = addressObjectTypeDao;
    }


    public List<AddrObject> getAddrObjectsByParentGuid(String guid, boolean isActual) {
        if (guid.equals(" ")) return getAddrObjectsStartList();
        else {
            String queryPart = isActual ? " and livestatus=1" : "";
            List<AddrObject> addrObjects = getEntities(
                    "select * from object where parentguid='" + guid + "'" + queryPart, AddrObject.class);
            if (!isActual) addrObjects = getAddrObjectsWithMaxEnddate(addrObjects);
            for (AddrObject addrObject : addrObjects) addrObject.setSHORTNAME(getFullType(addrObject));
            return addrObjects;
        }
    }

    private List<AddrObject> getAddrObjectsStartList() {
        List<AddrObject> addrObjects = getEntities(
                "select * from object where parentguid is null and livestatus=1", AddrObject.class);
        for (AddrObject addrObject : addrObjects) {
            addrObject.setSHORTNAME(getFullType(addrObject));
            if (addrObject.getFORMALNAME().contains("Чувашская")) addrObject.setFORMALNAME("Чувашская");
        }
        return addrObjects;
    }

    public List<AddrObject> getAddrObjectsByParams(LinkedHashMap<String, String> params, boolean isActual) {
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()) {
            if (key.equals("guid")) strings.add("aoguid='" + params.get(key) + "'");
            else strings.add(key + "='" + params.get(key) + "'");
        }
        if (isActual) strings.add("livestatus=1");
        String queryPart = String.join(" and ", strings);
        List<AddrObject> addrObjects = getEntities("select * from object where " + queryPart, AddrObject.class);
        if (addrObjects != null && addrObjects.size() > 0) {
            if (!isActual) addrObjects = getAddrObjectsWithMaxEnddate(addrObjects);
            for (AddrObject addrObject : addrObjects)
                addrObject.setFullAddress(getFullAddress(addrObject, addrObject.getPOSTALCODE()));
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
        for (int i = 1; i < addrObjects.size(); i++) {
            currentAddrObject = addrObjects.get(i);
            if (maxAddrObject.getAOGUID().equals(currentAddrObject.getAOGUID())) {
                if (maxAddrObject.getENDDATE() < currentAddrObject.getENDDATE()) maxAddrObject = currentAddrObject;
            } else {
                res.add(maxAddrObject);
                maxAddrObject = currentAddrObject;
            }
            if (i == addrObjects.size() - 1) res.add(maxAddrObject);
        }
        return res;
    }

    String getFullAddress(AddrObject addrObject, String postalcode) {
        if (addrObject.getFORMALNAME().contains("Чувашская")) addrObject.setFORMALNAME("Чувашская");
        String fullAddress = getFullType(addrObject) + " " + addrObject.getFORMALNAME();
        while (addrObject.getPARENTGUID() != null) {
            addrObject = getAddrObjectByGuid(addrObject.getPARENTGUID());
            if (addrObject.getFORMALNAME().contains("Чувашская")) addrObject.setFORMALNAME("Чувашская");
            fullAddress = getFullType(addrObject) + " " + addrObject.getFORMALNAME() + ", " + fullAddress;
        }
        return postalcode == null ? fullAddress : postalcode + ", " + fullAddress;
    }

    AddrObject getAddrObjectByGuid(String guid) {
        List<AddrObject> addrObjects = getEntities("select * from object where aoguid='" + guid + "'", AddrObject.class);
        addrObjects = getAddrObjectsWithMaxEnddate(addrObjects);
        return addrObjects.get(0);
    }

    private String getFullType(AddrObject addrObject) {
        if (addrObject.getFORMALNAME().contains("Чувашская")) return "республика";
        return addressObjectTypeDao.getFullName(addrObject.getAOLEVEL(), addrObject.getSHORTNAME());
    }

    public List<AddrObject> getAddrObjectsByName(String name, String type, boolean isActual) {
        String queryPart = isActual ? " and livestatus=1" : "";
        switch (type) {
            case ("all"):
                break;
            case ("Город"):
                queryPart = queryPart + " and (shortname='г' or shortname='г.')";
                break;
            case ("Область"):
                queryPart = queryPart + " and (shortname='обл' or shortname='обл.')";
                break;
            case ("Район"):
                queryPart = queryPart + " and shortname='р-н'";
                break;
            case ("Край"):
                queryPart = queryPart + " and shortname='край'";
                break;
            case ("Республика"):
                queryPart = queryPart + " and (shortname='Респ' or shortname='Респ.')";
                break;
            case ("Автономный округ"):
                queryPart = queryPart + " and shortname='АО'";
                break;
            case ("Автономная область"):
                queryPart = queryPart + " and shortname='Аобл'";
                break;
            case ("Улица"):
                queryPart = queryPart + " and (shortname='ул' or shortname='ул.')";
                break;
            case ("Проспект"):
                queryPart = queryPart + " and shortname='пр-кт'";
                break;
            case ("Село"):
                queryPart = queryPart + " and (shortname='с' or shortname='с.')";
                break;
            case ("Деревня"):
                queryPart = queryPart + " and (shortname='д' or shortname='д.')";
                break;
            case ("Поселок"):
                queryPart = queryPart + " and (shortname='п' or shortname='п.')";
                break;
            case ("Поселок городского типа"):
                queryPart = queryPart + " and (shortname='пгт' or shortname='пгт.')";
                break;
        }
        List<AddrObject> addrObjects = getEntities(
                "select * from object where formalname like '" + name + "%'" + queryPart, AddrObject.class);
        if (!isActual) addrObjects = getAddrObjectsWithMaxEnddate(addrObjects);
        for (AddrObject addrObject : addrObjects)
            addrObject.setFullAddress(getFullAddress(addrObject, addrObject.getPOSTALCODE()));
        return addrObjects;
    }
}
