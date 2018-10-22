package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.primary.House;
import system.model.primary.Room;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class RoomDao extends GenericDao<Room>{

    private FlatTypeDao flatTypeDao;
    private HouseDao houseDao;

    @Autowired
    public void setFlatTypeDao(FlatTypeDao flatTypeDao) {
        this.flatTypeDao = flatTypeDao;
    }

    @Autowired
    public void setHouseDao(HouseDao houseDao) {
        this.houseDao = houseDao;
    }

    public List<Room> getRoomsListByParentGuid(String guid) {
        List<Room> rooms = getEntities(
                "select * from room where houseguid=\""+guid+"\" and livestatus=1", Room.class);
        for (Room room:rooms) room.setType(getRoomType(room));
        return rooms;
    }

    public List<Room> getRoomsByParams(LinkedHashMap<String, String> params) {
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("roomguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        String queryPart = String.join(" and ", strings);
        List<Room> rooms = getEntities("select * from room where "+queryPart+" and livestatus=1", Room.class);
        if (rooms != null && rooms.size() > 0) {
            for (Room room:rooms) room.setFullAddress(getFullAddress(room));
            return rooms;
        }
        return null;
    }

    private String getFullAddress(Room room) {
        House house = houseDao.getHouseByGuid(room.getHOUSEGUID());
        if (house == null) return "HOUSE NOT FOUND(";                   //убрать
        room.setOkato(house.getOKATO());
        room.setOktmo(house.getOKTMO());
        room.setIfnsfl(house.getIFNSFL());
        room.setIfnsul(house.getIFNSUL());
        return houseDao.getFullAddress(room.getHOUSEGUID()) + ", " + getRoomType(room) + " " + room.getFLATNUMBER();
    }

    private String getRoomType(Room room){
        return flatTypeDao.getFlatType(room.getFLATTYPE());
    }
}
