package fias.repository;

import fias.domain.EstateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateStatusRepository extends JpaRepository<EstateStatus, Integer> {

    @Query("SELECT e.NAME from EstateStatus e where e.ESTSTATID=?1")
    String getEstateType(int number);
}
