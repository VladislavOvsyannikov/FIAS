package system.model;

import javax.persistence.*;

@Entity
@Table(name = "stead", schema = "fias")
public class Stead {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "steadguid", nullable = false)
    private String STEADGUID;

    @Column(name = "regioncode", nullable = false)
    private String REGIONCODE;

//    @Column(name = "updatedate", nullable = false)
//    private String UPDATEDATE;

//    @Column(name = "operstatus", nullable = false)
//    private int OPERSTATUS;

//    @Column(name = "startdate", nullable = false)
//    private String STARTDATE;

//    @Column(name = "enddate", nullable = false)
//    private String ENDDATE;

    @Column(name = "divtype", nullable = false)
    private int DIVTYPE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSTEADGUID() {
        return STEADGUID;
    }

    public void setSTEADGUID(String STEADGUID) {
        this.STEADGUID = STEADGUID;
    }

    public String getREGIONCODE() {
        return REGIONCODE;
    }

    public void setREGIONCODE(String REGIONCODE) {
        this.REGIONCODE = REGIONCODE;
    }

    public int getDIVTYPE() {
        return DIVTYPE;
    }

    public void setDIVTYPE(int DIVTYPE) {
        this.DIVTYPE = DIVTYPE;
    }
}


