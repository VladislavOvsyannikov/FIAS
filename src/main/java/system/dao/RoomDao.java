package system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import system.model.FlatType;
import system.model.Room;

import java.util.List;

@Repository
public class RoomDao extends GenericDao<Room>{

    private FlatTypeDao flatTypeDao;

    @Autowired
    public void setFlatTypeDao(FlatTypeDao flatTypeDao) {
        this.flatTypeDao = flatTypeDao;
    }

    public List<Room> getRoomsListByGuid(String guid) {
        return setFullRoomType(getEntities(
                "select * from room where houseguid=\""+guid+"\" and livestatus=1", Room.class));
    }

    private List<Room> setFullRoomType(List<Room> rooms){
        for (Room room:rooms) room.setType(flatTypeDao.getFlatType(room.getFLATTYPE()));
        return rooms;
    }
}
