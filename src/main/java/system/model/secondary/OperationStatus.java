package system.model.secondary;

import javax.persistence.*;

/**
 * Информация по статусу действия
 */

@Entity
@Table(name = "operation_status", schema = "fias")
public class OperationStatus {

    @Id
    @Column(name = "operstatid", nullable = false)
    private int OPERSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getOPERSTATID() {
        return OPERSTATID;
    }

    public void setOPERSTATID(int OPERSTATID) {
        this.OPERSTATID = OPERSTATID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
