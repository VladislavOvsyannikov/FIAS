package system.model;

import javax.persistence.*;

/**
 * Информация по статусу статусу актуальности
 * 0 - актуальный,
 * 1-98 – исторический (кроме 51),
 * 51 - переподчиненный,
 * 99 - несуществующий
 */

@Entity
@Table(name = "current_status", schema = "fias")
public class CurrentStatus {

    @Id
    @Column(name = "curentstid", nullable = false)
    private int CURENTSTID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getCURENTSTID() {
        return CURENTSTID;
    }

    public void setCURENTSTID(int CURENTSTID) {
        this.CURENTSTID = CURENTSTID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
