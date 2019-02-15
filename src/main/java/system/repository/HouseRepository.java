package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.House;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, String> {

    @Query("SELECT new House(h.HOUSEGUID, h.ENDDATE, h.HOUSENUM, h.BUILDNUM, h.STRUCNUM, h.ESTSTATUS, h.STRSTATUS) " +
            "from House h where h.AOGUID=?1")
    List<House> getHousesByParentguid(String parentguid);

    @Query("SELECT h from House h where h.HOUSEGUID=?1")
    List<House> getHousesByHouseguid(String houseguid);
}
