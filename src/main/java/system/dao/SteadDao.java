package system.dao;

import org.springframework.stereotype.Repository;
import system.model.Object;
import system.model.Stead;

import java.util.List;

@Repository
public class SteadDao extends GenericDao<Stead> {

    public List<Stead> getSteadsListByGuid(String guid) {
        return getEntities(
                "select * from stead where parentguid=\""+guid+"\" and livestatus=1", Stead.class);
    }
}

