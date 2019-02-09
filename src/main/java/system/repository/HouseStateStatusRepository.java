package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.HouseStateStatus;

@Repository
public interface HouseStateStatusRepository extends JpaRepository<HouseStateStatus, Integer> {

    @Query("select h.NAME from HouseStateStatus h where h.HOUSESTID=?1")
    String getHouseStateStatus(int number);
}
