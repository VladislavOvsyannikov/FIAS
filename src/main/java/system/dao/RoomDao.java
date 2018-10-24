package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.House;
import system.model.Room;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Room> getRoomsListByParentGuid(String guid, boolean isActual) {
        String queryPart = isActual? " and livestatus=1": "";
        List<Room> rooms = getEntities(
                "select * from room where houseguid=\""+guid+"\"" + queryPart, Room.class);
        if(!isActual) rooms = getRoomsWithMaxEnddate(rooms);
        for (Room room:rooms) room.setType(getRoomType(room));
        return rooms;
    }

    public List<Room> getRoomsByParams(LinkedHashMap<String, String> params, boolean isActual) {
        List<String> strings = new ArrayList<>();
        for (String key : params.keySet()){
            if (key.equals("guid") && !params.get(key).equals("")) strings.add("roomguid=\""+params.get(key)+"\"");
            else if (!params.get(key).equals("")) strings.add(key+"=\""+params.get(key)+"\"");
        }
        if (isActual) strings.add("livestatus=1");
        String queryPart = String.join(" and ", strings);
        List<Room> rooms = getEntities("select * from room where "+queryPart, Room.class);
        if (rooms != null && rooms.size() > 0) {
            if (!isActual) rooms = getRoomsWithMaxEnddate(rooms);
            for (Room room:rooms) room.setFullAddress(getFullAddress(room));
            return rooms;
        }
        return null;
    }

    private List<Room> getRoomsWithMaxEnddate(List<Room> rooms) {
        if (rooms.size() <= 1) return rooms;
        rooms.sort(Comparator.comparing(Room::getROOMGUID));
        List<Room> res = new ArrayList<>();
        Room maxRoom = rooms.get(0);
        Room currentRoom;
        for (int i=1; i<rooms.size(); i++){
            currentRoom = rooms.get(i);
            if (maxRoom.getROOMGUID().equals(currentRoom.getROOMGUID())){
                if (Integer.parseInt(maxRoom.getENDDATE().replaceAll("-","")) <
                        Integer.parseInt(currentRoom.getENDDATE().replaceAll("-","")))
                    maxRoom = currentRoom;
            }else{
                res.add(maxRoom);
                maxRoom = currentRoom;
            }
            if (i == rooms.size()-1) res.add(maxRoom);
        }
        return res;
    }

    private String getFullAddress(Room room) {
        House house = houseDao.getHouseByGuid(room.getHOUSEGUID());
        if (house == null) return "HOUSE NOT FOUND(";                   //убрать
        room.setOkato(house.getOKATO());
        room.setOktmo(house.getOKTMO());
        room.setIfnsfl(house.getIFNSFL());
        room.setIfnsul(house.getIFNSUL());
        room.setStateStatus(houseDao.getStateStatus(house));
        return houseDao.getFullAddress(room.getHOUSEGUID()) + ", " + getRoomType(room) + " " + room.getFLATNUMBER();
    }

    private String getRoomType(Room room){
        return flatTypeDao.getFlatType(room.getFLATTYPE());
    }
}
