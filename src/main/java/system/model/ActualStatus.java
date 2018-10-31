package system.model;

import javax.persistence.*;

/**
 * Статус актуальности
 * 0 – Не актуальный
 * 1 – Актуальный
 */

@Entity
@Table(name = "actual_status", schema = "fias")
public class ActualStatus {

    @Id
    @Column(name = "actstatid", nullable = false)
    private int ACTSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;


    public int getACTSTATID() {
        return ACTSTATID;
    }
    public void setACTSTATID(int ACTSTATID) {
        this.ACTSTATID = ACTSTATID;
    }
    public String getNAME() {
        return NAME;
    }
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
