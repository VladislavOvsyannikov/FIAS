package system.model;

import javax.persistence.*;

/**
 * Информация по признакам владения
 * 0 – Не определено
 * 1 – Владение
 * 2 - Дом
 * 3 - Домовладение
 * 4 - Гараж
 * 5 - Здание
 * 6 - Шахта
 */

@Entity
@Table(name = "estate_status", schema = "fias")
public class EstateStatus {

    @Id
    @Column(name = "eststatid", nullable = false)
    private int ESTSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getESTSTATID() {
        return ESTSTATID;
    }

    public void setESTSTATID(int ESTSTATID) {
        this.ESTSTATID = ESTSTATID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
