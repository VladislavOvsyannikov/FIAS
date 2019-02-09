package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.FlatType;

@Repository
public interface FlatTypeRepository extends JpaRepository<FlatType, Integer> {

    @Query("SELECT f.NAME from FlatType f where f.FLTYPEID=?1")
    String getFlatType(int number);
}
