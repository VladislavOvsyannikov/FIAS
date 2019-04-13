package fias.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по типу нормативного документа
 */

@Entity
@Table(name = "normative_document_type")
@Data
@EqualsAndHashCode(of = "NDTYPEID")
public class NormativeDocumentType {

    @Id
    @Column(name = "ndtypeid", nullable = false)
    private int NDTYPEID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
