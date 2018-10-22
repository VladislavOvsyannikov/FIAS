package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.primary.Stead;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class SteadDao extends GenericDao<Stead> {

    private ObjectDao objectDao;

    @Autowired
    public void setObjectDao(ObjectDao objectDao) {
        this.objectDao = objectDao;
    }


    public List<Stead> getSteadsByParentGuid(String guid) {
        return getEntities(
                "select * from stead where parentguid=\""+guid+"\" and livestatus=1", Stead.class);
    }

    public List<Stead> getSteadsByParams(LinkedHashMap<String, String> params) {
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("steadguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        String queryPart = String.join(" and ", strings);
        List<Stead> steads = getEntities("select * from stead where "+queryPart+" and livestatus=1", Stead.class);
        if (steads != null && steads.size() > 0) {
            for (Stead stead:steads) stead.setFullAddress(getFullAddress(stead));
            return steads;
        }
        return null;
    }

    private String getFullAddress(Stead stead) {
        return objectDao.getFullAddress(stead.getPARENTGUID()) + ", участок " + stead.getNUMBER();
    }
}

