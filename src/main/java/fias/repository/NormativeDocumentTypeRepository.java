package fias.repository;

import fias.domain.NormativeDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NormativeDocumentTypeRepository extends JpaRepository<NormativeDocumentType, Integer> {
}
