package fias.repository;

import fias.domain.StructureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureStatusRepository extends JpaRepository<StructureStatus, Integer> {

    @Query("SELECT s.NAME from StructureStatus s where s.STRSTATID=?1")
    String getStructureType(int number);
}
