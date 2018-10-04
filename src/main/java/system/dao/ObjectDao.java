package system.dao;

import org.springframework.stereotype.Repository;
import system.model.Object;

import java.util.List;

@Repository
public class ObjectDao extends GenericDao<Object>{

    public List<Object> getObjectsStartList() {
        return getEntities(
                "select * from object where parentguid is null and livestatus=1", Object.class);
    }

    public List<Object> getObjectsListByGuid(String guid) {
        if (guid.equals(" ")){
            return getObjectsStartList();
        }else return getEntities(
                "select * from object where parentguid=\""+guid+"\" and livestatus=1", Object.class);
    }
}
