package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.AddrObject;
import system.model.House;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class HouseDao extends GenericDao<House>{

    private AddrObjectDao addrObjectDao;
    private EstateStatusDao estateStatusDao;
    private StructureStatusDao structureStatusDao;
    private HouseStateStatusDao houseStateStatusDao;

    @Autowired
    public void setHouseStateStatusDao(HouseStateStatusDao houseStateStatusDao) {
        this.houseStateStatusDao = houseStateStatusDao;
    }
    @Autowired
    public void setAddrObjectDao(AddrObjectDao addrObjectDao) {
        this.addrObjectDao = addrObjectDao;
    }
    @Autowired
    public void setEstateStatusDao(EstateStatusDao estateStatusDao) {
        this.estateStatusDao = estateStatusDao;
    }
    @Autowired
    public void setStructureStatusDao(StructureStatusDao structureStatusDao) {
        this.structureStatusDao = structureStatusDao;
    }


    public List<House> getHousesByParentGuid(String guid, boolean isActual) {
        List<House> houses = getEntities(
                "select * from house where aoguid=\""+guid+"\"", House.class);
        if (isActual) {
            int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            houses.removeIf(house -> currentDate > house.getENDDATE());
        } else houses = getHousesWithMaxEnddate(houses);
        for (House house : houses){
            house.setType(getHouseType(house));
            house.setName(getHouseName(house));
        }
        return houses;
    }

    public List<House> getHousesByParams(LinkedHashMap<String, String> params, boolean isActual) {
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid")) strings.add("houseguid=\""+params.get(key)+"\"");
            else strings.add(key+"=\""+params.get(key)+"\"");
        }
        String queryPart = String.join(" and ", strings);
        List<House> houses = getEntities("select * from house where "+queryPart, House.class);
        if (houses != null && houses.size() > 0) {
            if (isActual) {
                int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                houses.removeIf(house -> currentDate > house.getENDDATE());
            }else houses = getHousesWithMaxEnddate(houses);
            for (House house:houses) {
                house.setFullAddress(getFullAddress(house, house.getPOSTALCODE()));
                house.setStateStatus(getStateStatus(house));
            }
            return houses;
        }
        return null;
    }

    private List<House> getHousesWithMaxEnddate(List<House> houses) {
        if (houses.size() <= 1) return houses;
        houses.sort(Comparator.comparing(House::getHOUSEGUID));
        List<House> res = new ArrayList<>();
        House maxObject = houses.get(0);
        House currentObject;
        for (int i=1; i<houses.size(); i++){
            currentObject = houses.get(i);
            if (maxObject.getHOUSEGUID().equals(currentObject.getHOUSEGUID())){
                if (maxObject.getENDDATE() < currentObject.getENDDATE()) maxObject = currentObject;
            }else{
                res.add(maxObject);
                maxObject = currentObject;
            }
            if (i == houses.size()-1) res.add(maxObject);
        }
        return res;
    }

    House getHouseByGuid(String guid){
        List<House> houses = getEntities("select * from house where houseguid=\""+guid+"\"", House.class);
        houses = getHousesWithMaxEnddate(houses);
        return houses.get(0);
    }

    String getFullAddress(House house, String postalcode) {
        AddrObject addrObject = addrObjectDao.getAddrObjectByGuid(house.getAOGUID());
        return addrObjectDao.getFullAddress(addrObject, postalcode) + ", " + getHouseType(house) + " " + getHouseName(house);
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

    String getStateStatus(House house){
        return houseStateStatusDao.getStateStatus(house.getSTATSTATUS());
    }
}
