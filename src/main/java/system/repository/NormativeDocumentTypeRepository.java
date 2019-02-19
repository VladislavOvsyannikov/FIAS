package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.domain.NormativeDocumentType;

@Repository
public interface NormativeDocumentTypeRepository extends JpaRepository<NormativeDocumentType, Integer> {
}
