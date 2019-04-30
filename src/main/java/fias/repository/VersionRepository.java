package fias.repository;

import fias.domain.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepository extends JpaRepository<Version, Integer> {

    @Query("select v.version from Version v order by v.id desc")
    List<String> getVersions();
}
