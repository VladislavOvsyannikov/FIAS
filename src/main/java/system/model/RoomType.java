package system.model;

import javax.persistence.*;

/**
 * Типы комнат
 * 0 – Не определено
 * 1 – Комната
 * 2 - Помещение
 */

@Entity
@Table(name = "room_type", schema = "fias")
public class RoomType {

    @Id
    @Column(name = "rmtypeid", nullable = false)
    private int RMTYPEID;

    @Column(name = "name", nullable = false)
    private String NAME;


    public int getRMTYPEID() {
        return RMTYPEID;
    }

    public void setRMTYPEID(int RMTYPEID) {
        this.RMTYPEID = RMTYPEID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
