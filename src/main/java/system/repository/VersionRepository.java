package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.domain.Version;

@Repository
public interface VersionRepository extends JpaRepository<Version, Integer> {

    @Query("select v.version from Version v order by v.id desc")
    String getCurrentVersion();
}
