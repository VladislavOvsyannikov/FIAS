package system.model;

import javax.persistence.*;

/**
 * Информация по статусу интервалов домов
 * 0 – Не определено
 * 1 – Обычный
 * 2 - Четный
 * 3 - Нечетный
 */

@Entity
@Table(name = "interval_status", schema = "fias")
public class IntervalStatus {

    @Id
    @Column(name = "intvstatid", nullable = false)
    private int INTVSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getINTVSTATID() {
        return INTVSTATID;
    }

    public void setINTVSTATID(int INTVSTATID) {
        this.INTVSTATID = INTVSTATID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
