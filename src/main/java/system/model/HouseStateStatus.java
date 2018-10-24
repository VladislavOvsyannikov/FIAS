package system.model;

import javax.persistence.*;

/**
 * Информация по статусу состояния домов
 */

@Entity
@Table(name = "house_state_status", schema = "fias")
public class HouseStateStatus {

    @Id
    @Column(name = "housestid", nullable = false)
    private int HOUSESTID;

    @Column(name = "name", nullable = false)
    private String NAME;



    public int getHOUSESTID() {
        return HOUSESTID;
    }

    public void setHOUSESTID(int HOUSESTID) {
        this.HOUSESTID = HOUSESTID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
