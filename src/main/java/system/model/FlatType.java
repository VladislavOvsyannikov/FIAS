package system.model;

import javax.persistence.*;

/**
 * Информация по типам помещений
 * 0 – Не определено
 * 1 – Помещение
 * 2 - Квартира
 * 3 - Офис
 * 4 - Комната
 * 5 - Рабочий участок
 * 6 - Склад
 * 7 - Торговый зал
 * 8 - Цех
 * 9 - Павильон
 */

@Entity
@Table(name = "flat_type", schema = "fias")
public class FlatType {

    @Id
    @Column(name = "fltypeid", nullable = false)
    private int FLTYPEID;

    @Column(name = "name", nullable = false)
    private String NAME;

    public int getFLTYPEID() {
        return FLTYPEID;
    }

    public void setFLTYPEID(int FLTYPEID) {
        this.FLTYPEID = FLTYPEID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
