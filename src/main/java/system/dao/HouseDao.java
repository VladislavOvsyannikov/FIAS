package system.dao;

import org.springframework.stereotype.Repository;
import system.model.House;

import java.util.List;

@Repository
public class HouseDao extends GenericDao<House>{

    public List<House> getHousesListByGuid(String guid) {
        return getEntities(
                "select * from house where parentguid=\""+guid+"\"", House.class);
    }
}
