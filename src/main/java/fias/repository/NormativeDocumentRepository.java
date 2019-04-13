package fias.repository;

import fias.domain.NormativeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NormativeDocumentRepository extends JpaRepository<NormativeDocument, String> {
}
