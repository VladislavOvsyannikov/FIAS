package system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import system.domain.NormativeDocument;

@Repository
public interface NormativeDocumentRepository extends JpaRepository<NormativeDocument, String> {
}
