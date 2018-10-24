package system.model;

import javax.persistence.*;

/**
 * Информация по статусу интервалов домов
 * 0 – Не определено
 * 1 – Строение
 * 2 - Сооружение
 * 3 - Литер
 */

@Entity
@Table(name = "structure_status", schema = "fias")
public class StructureStatus {

    @Id
    @Column(name = "strstatid", nullable = false)
    private int STRSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getSTRSTATID() {
        return STRSTATID;
    }

    public void setSTRSTATID(int STRSTATID) {
        this.STRSTATID = STRSTATID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
