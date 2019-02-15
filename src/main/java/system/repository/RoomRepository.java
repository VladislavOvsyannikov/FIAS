package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("SELECT new Room(r.ROOMGUID, r.ENDDATE, r.FLATTYPE, r.FLATNUMBER) " +
            "from Room r where r.HOUSEGUID=?1 and r.LIVESTATUS=1")
    List<Room> getActualRoomsByHouseguid(String houseguid);

    @Query("SELECT new Room(r.ROOMGUID, r.ENDDATE, r.FLATTYPE, r.FLATNUMBER) from Room r where r.HOUSEGUID=?1")
    List<Room> getRoomsByHouseguid(String houseguid);

    @Query("SELECT r from Room r where r.ROOMGUID=?1")
    List<Room> getRoomsByRoomguid(String roomguid);
}
