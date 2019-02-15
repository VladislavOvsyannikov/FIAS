package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.AddrObject;

import java.util.List;

@Repository
public interface AddrObjectRepository extends JpaRepository<AddrObject, String> {

    @Query("SELECT new AddrObject(a.AOGUID, a.FORMALNAME, a.SHORTNAME, a.AOLEVEL, a.ENDDATE) " +
            "from AddrObject a where a.PARENTGUID is null and a.LIVESTATUS=1")
    List<AddrObject> getAddrObjectsStartList();

    @Query("SELECT new AddrObject(a.AOGUID, a.FORMALNAME, a.SHORTNAME, a.AOLEVEL, a.ENDDATE) " +
            "from AddrObject a where a.PARENTGUID=?1 and a.LIVESTATUS=1")
    List<AddrObject> getActualAddrObjectsByParentGuid(String parentguid);

    @Query("SELECT new AddrObject(a.AOGUID, a.FORMALNAME, a.SHORTNAME, a.AOLEVEL, a.ENDDATE) " +
            "from AddrObject a where a.PARENTGUID=?1")
    List<AddrObject> getAddrObjectsByParentguid(String parentguid);

    @Query("SELECT a from AddrObject a where a.AOGUID=?1")
    List<AddrObject> getAddrObjectsByAoguid(String aoguid);
}
