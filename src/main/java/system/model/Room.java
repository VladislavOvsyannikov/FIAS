package system.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room", schema = "fias")
public class Room {

    @Id
    @Column(name = "roomguid", nullable = false)
    private String ROOMGUID;

    @Column(name = "flatnumber", nullable = false)
    private String FLATNUMBER;

    @Column(name = "flattype", nullable = false)
    private int FLATTYPE;

//    private String ROOMNUMBER;
//    private int ROOMTYPE;

    @Column(name = "regioncode", nullable = false)
    private String REGIONCODE;

//    private String POSTALCODE;

    @Column(name = "updatedate", nullable = false)
    private String UPDATEDATE;

    @Column(name = "houseid", nullable = false)
    private String HOUSEGUID;

    @Column(name = "roomid", nullable = false)
    private String ROOMID;

//    private String PREVID;
//    private String NEXTID;

    @Column(name = "startdate", nullable = false)
    private String STARTDATE;

    @Column(name = "enddate", nullable = false)
    private String ENDDATE;

    @Column(name = "livestatus", nullable = false)
    private int LIVESTATUS;

//    private String NORMDOC;

    @Column(name = "operstatus", nullable = false)
    private int OPERSTATUS;

//    private String CADNUM;
//    private String ROOMCADNUM;


    public String getROOMGUID() {
        return ROOMGUID;
    }

    public void setROOMGUID(String ROOMGUID) {
        this.ROOMGUID = ROOMGUID;
    }

    public String getFLATNUMBER() {
        return FLATNUMBER;
    }

    public void setFLATNUMBER(String FLATNUMBER) {
        this.FLATNUMBER = FLATNUMBER;
    }

    public int getFLATTYPE() {
        return FLATTYPE;
    }

    public void setFLATTYPE(int FLATTYPE) {
        this.FLATTYPE = FLATTYPE;
    }

    public String getREGIONCODE() {
        return REGIONCODE;
    }

    public void setREGIONCODE(String REGIONCODE) {
        this.REGIONCODE = REGIONCODE;
    }

    public String getUPDATEDATE() {
        return UPDATEDATE;
    }

    public void setUPDATEDATE(String UPDATEDATE) {
        this.UPDATEDATE = UPDATEDATE;
    }

    public String getHOUSEGUID() {
        return HOUSEGUID;
    }

    public void setHOUSEGUID(String HOUSEGUID) {
        this.HOUSEGUID = HOUSEGUID;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public String getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(String STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public String getENDDATE() {
        return ENDDATE;
    }

    public void setENDDATE(String ENDDATE) {
        this.ENDDATE = ENDDATE;
    }

    public int getLIVESTATUS() {
        return LIVESTATUS;
    }

    public void setLIVESTATUS(int LIVESTATUS) {
        this.LIVESTATUS = LIVESTATUS;
    }

    public int getOPERSTATUS() {
        return OPERSTATUS;
    }

    public void setOPERSTATUS(int OPERSTATUS) {
        this.OPERSTATUS = OPERSTATUS;
    }

    @Override
    public String toString() {
        return "Room{" +
                "ROOMGUID='" + ROOMGUID + '\'' +
                ", FLATNUMBER='" + FLATNUMBER + '\'' +
                ", FLATTYPE=" + FLATTYPE +
                ", REGIONCODE='" + REGIONCODE + '\'' +
                ", UPDATEDATE='" + UPDATEDATE + '\'' +
                ", HOUSEGUID='" + HOUSEGUID + '\'' +
                ", ROOMID='" + ROOMID + '\'' +
                ", STARTDATE='" + STARTDATE + '\'' +
                ", ENDDATE='" + ENDDATE + '\'' +
                ", LIVESTATUS=" + LIVESTATUS +
                ", OPERSTATUS=" + OPERSTATUS +
                '}';
    }
}
