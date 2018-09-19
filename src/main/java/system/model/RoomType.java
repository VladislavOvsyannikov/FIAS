package system.model;

import javax.persistence.*;

@Entity
@Table(name = "roomtype", schema = "fias")
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


    @Override
    public String toString() {
        return "RoomType{" +
                "RMTYPEID=" + RMTYPEID +
                ", NAME='" + NAME + '\'' +
                '}';
    }
}
