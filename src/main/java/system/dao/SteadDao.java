package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.Stead;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class SteadDao extends GenericDao<Stead> {

    private ObjectDao objectDao;

    @Autowired
    public void setObjectDao(ObjectDao objectDao) {
        this.objectDao = objectDao;
    }


    public List<Stead> getSteadsByParentGuid(String guid, boolean isActual) {
        String queryPart = isActual? " and livestatus=1": "";
        List<Stead> steads = getEntities(
                "select * from stead where parentguid=\""+guid+"\"" + queryPart, Stead.class);
        if (!isActual) steads = getSteadsWithMaxEnddate(steads);
        return steads;
    }

    public List<Stead> getSteadsByParams(LinkedHashMap<String, String> params, boolean isActual) {
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("steadguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        if (isActual) strings.add("livestatus=1");
        String queryPart = String.join(" and ", strings);
        List<Stead> steads = getEntities("select * from stead where "+queryPart, Stead.class);
        if (steads != null && steads.size() > 0) {
            if (!isActual) steads = getSteadsWithMaxEnddate(steads);
            for (Stead stead:steads) stead.setFullAddress(getFullAddress(stead));
            return steads;
        }
        return null;
    }

    private List<Stead> getSteadsWithMaxEnddate(List<Stead> steads) {
        if (steads.size() <= 1) return steads;
        steads.sort(Comparator.comparing(Stead::getSTEADGUID));
        List<Stead> res = new ArrayList<>();
        Stead maxStead = steads.get(0);
        Stead currentStead;
        for (int i=1; i<steads.size(); i++){
            currentStead = steads.get(i);
            if (maxStead.getSTEADGUID().equals(currentStead.getSTEADGUID())){
                if (maxStead.getENDDATE() < currentStead.getENDDATE()) maxStead = currentStead;
            }else{
                res.add(maxStead);
                maxStead = currentStead;
            }
            if (i == steads.size()-1) res.add(maxStead);
        }
        return res;
    }

    private String getFullAddress(Stead stead) {
        return objectDao.getFullAddress(stead.getPARENTGUID()) + ", участок " + stead.getNUMBER();
    }
}

