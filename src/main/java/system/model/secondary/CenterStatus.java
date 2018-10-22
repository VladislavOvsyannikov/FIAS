package system.model.secondary;

import javax.persistence.*;

/**
 * Информация по статусу центра
 * 0 – Объект не является центром административно-территориального образования
 * 1 – Объект является центром района
 * 2 - Объект является центром (столицей) региона
 * 3 - Объект является одновременно и центром района и центром региона
 * 4 - Центральный район, т.е. район, в котором находится центр региона (только для объектов 2-го уровня)
 */

@Entity
@Table(name = "center_status", schema = "fias")
public class CenterStatus {

    @Id
    @Column(name = "centerstid", nullable = false)
    private int CENTERSTID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getCENTERSTID() {
        return CENTERSTID;
    }

    public void setCENTERSTID(int CENTERSTID) {
        this.CENTERSTID = CENTERSTID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
