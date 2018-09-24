package system.model;

import javax.persistence.*;

/**
 * Информация по типу нормативного документа
 */

@Entity
@Table(name = "normative_document_type", schema = "fias")
public class NormativeDocumentType {

    @Id
    @Column(name = "ndtypeid", nullable = false)
    private int NDTYPEID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getNDTYPEID() {
        return NDTYPEID;
    }

    public void setNDTYPEID(int NDTYPEID) {
        this.NDTYPEID = NDTYPEID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
