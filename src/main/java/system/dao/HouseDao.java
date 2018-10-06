package system.dao;

import org.springframework.stereotype.Repository;
import system.model.House;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class HouseDao extends GenericDao<House>{

    public List<House> getHousesListByGuid(String guid) {
        List<House> houses = getEntities(
                "select * from house where aoguid=\""+guid+"\"", House.class);
        int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        houses.removeIf(house ->
                currentDate > Integer.parseInt(house.getENDDATE().replaceAll("-", "")));
        return houses;
    }
}
