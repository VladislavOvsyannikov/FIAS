package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.primary.House;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class HouseDao extends GenericDao<House>{

    private ObjectDao objectDao;
    private EstateStatusDao estateStatusDao;
    private StructureStatusDao structureStatusDao;

    @Autowired
    public void setObjectDao(ObjectDao objectDao) {
        this.objectDao = objectDao;
    }
    @Autowired
    public void setEstateStatusDao(EstateStatusDao estateStatusDao) {
        this.estateStatusDao = estateStatusDao;
    }
    @Autowired
    public void setStructureStatusDao(StructureStatusDao structureStatusDao) {
        this.structureStatusDao = structureStatusDao;
    }


    public List<House> getHousesByParentGuid(String guid) {
        List<House> houses = getEntities(
                "select * from house where aoguid=\""+guid+"\"", House.class);
        int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        houses.removeIf(house -> currentDate > house.getENDDATE());
        for (House house : houses){
            house.setType(getHouseType(house));
            house.setName(getHouseName(house));
        }
        return houses;
    }

    public List<House> getHousesByParams(LinkedHashMap<String, String> params) {
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("houseguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        String queryPart = String.join(" and ", strings);
        List<House> houses = getEntities("select * from house where "+queryPart, House.class);
        if (houses != null && houses.size() > 0) {
            int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            houses.removeIf(house -> currentDate > house.getENDDATE());
            for (House house:houses) house.setFullAddress(getFullAddress(house));
            return houses;
        }
        return null;
    }

    public House getHouseByGuid(String guid){
        List<House> houses = getEntities("select * from house where houseguid=\""+guid+"\"", House.class);
        if (houses.size() == 0) return null;
        int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        houses.removeIf(house -> currentDate > house.getENDDATE());
        return houses.get(0);
    }

    public String getFullAddress(String guid) {
        House house = getHouseByGuid(guid);
        return objectDao.getFullAddress(house.getAOGUID()) + ", " + getHouseType(house) + " " + getHouseName(house);
    }

    private String getFullAddress(House house) {
        return objectDao.getFullAddress(house.getAOGUID()) + ", " + getHouseType(house) + " " + getHouseName(house);
    }

    private String getHouseType(House house){
        if (house.getESTSTATUS() != 0) return estateStatusDao.getEstateType(house.getESTSTATUS());
        else if (house.getSTRSTATUS() != 0)
            return structureStatusDao.getStructureType(house.getSTRSTATUS());
        return "";
    }

    private String getHouseName(House house){
        String name = "";
        if (house.getHOUSENUM() != null) name += house.getHOUSENUM();
        if (house.getBUILDNUM() != null) name += " к. " + house.getBUILDNUM();
        if (house.getSTRUCNUM() != null) name += " стр. " + house.getSTRUCNUM();
        if (house.getHOUSENUM() == null && house.getBUILDNUM() == null && house.getSTRUCNUM() != null)
            name = house.getSTRUCNUM();
        return name;
    }
}
