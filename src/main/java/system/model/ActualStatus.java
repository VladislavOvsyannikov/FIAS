package system.model;


import javax.persistence.*;


@Entity
@Table(name = "actualstatus", schema = "fias")
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

    @Override
    public String toString() {
        return "ActualStatus{" +
                "ACTSTATID=" + ACTSTATID +
                ", NAME='" + NAME + '\'' +
                '}';
    }
}
