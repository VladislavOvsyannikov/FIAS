package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.Stead;

import java.util.List;

@Repository
public interface SteadRepository extends JpaRepository<Stead, String> {

    @Query("SELECT new Stead(s.STEADGUID, s.NUMBER, s.ENDDATE) from Stead s where s.PARENTGUID=?1 and s.LIVESTATUS=1")
    List<Stead> getActualSteadsByParentguid(String parentguid);

    @Query("SELECT new Stead(s.STEADGUID, s.NUMBER, s.ENDDATE) from Stead s where s.PARENTGUID=?1")
    List<Stead> getSteadsByParentguid(String parentguid);

    @Query("SELECT s from Stead s where s.STEADGUID=?1")
    List<Stead> getSteadsBySteadguid(String steadguid);
}
