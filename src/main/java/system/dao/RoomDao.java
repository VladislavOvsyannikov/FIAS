package system.dao;

import org.springframework.stereotype.Repository;
import system.model.Room;

import java.util.List;

@Repository
public class RoomDao extends GenericDao<Room>{

    public List<Room> getRoomsListByGuid(String guid) {
        return getEntities(
                "select * from room where parentguid=\""+guid+"\" and livestatus=1", Room.class);
    }
}
