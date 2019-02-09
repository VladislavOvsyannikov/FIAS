package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.AddressObjectType;

@Repository
public interface AddressObjectTypeRepository extends JpaRepository<AddressObjectType, String> {

    @Query("SELECT a.SOCRNAME from AddressObjectType a where a.LEVEL=?1 and a.SCNAME=?2")
    String getFullAddrObjectType(int level, String shortName);
}
