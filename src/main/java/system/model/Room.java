package system.model;

import javax.persistence.*;

/**
 * Сведения о помещениях
 */

@Entity
@Table(name = "room", schema = "fias", indexes = {
        @Index(columnList = "houseguid", name = "houseguidroom"),
        @Index(columnList = "roomguid", name = "roomguid"),
        @Index(columnList = "postalcode", name = "postalcode")})
public class Room {

    @Transient
    private String fullAddress;

    @Id
    @Column(name = "roomid", nullable = false, unique = true, length = 32)
    private String ROOMID;

    @Column(name = "roomguid", nullable = false, length = 32)
    private String ROOMGUID;

    //Идентификатор родительского объекта (дома)
    @Column(name = "houseguid", length = 32)
    private String HOUSEGUID;

    //Признак действующего адресного объекта
    @Column(name = "livestatus", length = 1)
    private int LIVESTATUS;

    //Тип помещения
    @Column(name = "flattype", length = 1)
    private int FLATTYPE;

    //Номер помещения или офиса
    @Column(name = "flatnumber", length = 100)
    private String FLATNUMBER;

    //Почтовый индекс
    @Column(name = "postalcode", length = 6)
    private String POSTALCODE;

    //Кадастровый номер помещения
    @Column(name = "cadnum", length = 100)
    private String CADNUM;

    //Кадастровый номер комнаты в помещении
    @Column(name = "roomcadnum", length = 100)
    private String ROOMCADNUM;

    //Начало действия записи
    @Column(name = "startdate", length = 8)
    private int STARTDATE;

    //Окончание действия записи
    @Column(name = "enddate", length = 8)
    private int ENDDATE;


//    //Внешний ключ на нормативный документ
//    @Column(name = "normdoc")
//    private String NORMDOC;

    @Transient
    private String type;

    @Transient
    private String stateStatus;

    @Transient
    private String okato;

    @Transient
    private String oktmo;

    @Transient
    private String ifnsfl;

    @Transient
    private String ifnsul;

//    //Тип комнаты
//    @Column(name = "roomtype")
//    private int ROOMTYPE;

//    //Код региона
//    @Column(name = "regioncode")
//    private String REGIONCODE;

//    //Дата внесения записи
//    @Column(name = "updatedate")
//    private String UPDATEDATE;

//    //Идентификатор записи связывания с предыдушей исторической записью
//    @Column(name = "previd")
//    private String PREVID;
//
//    //Идентификатор записи  связывания с последующей исторической записью
//    @Column(name = "nextid")
//    private String NEXTID;
//
//    //Причина появления записи
//    @Column(name = "operstatus")
//    private int OPERSTATUS;


    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public String getROOMGUID() {
        return ROOMGUID;
    }

    public void setROOMGUID(String ROOMGUID) {
        this.ROOMGUID = ROOMGUID;
    }

    public String getHOUSEGUID() {
        return HOUSEGUID;
    }

    public void setHOUSEGUID(String HOUSEGUID) {
        this.HOUSEGUID = HOUSEGUID;
    }

    public int getLIVESTATUS() {
        return LIVESTATUS;
    }

    public void setLIVESTATUS(int LIVESTATUS) {
        this.LIVESTATUS = LIVESTATUS;
    }

    public int getFLATTYPE() {
        return FLATTYPE;
    }

    public void setFLATTYPE(int FLATTYPE) {
        this.FLATTYPE = FLATTYPE;
    }

    public String getFLATNUMBER() {
        return FLATNUMBER;
    }

    public void setFLATNUMBER(String FLATNUMBER) {
        this.FLATNUMBER = FLATNUMBER;
    }

    public String getPOSTALCODE() {
        return POSTALCODE;
    }

    public void setPOSTALCODE(String POSTALCODE) {
        this.POSTALCODE = POSTALCODE;
    }

    public int getSTARTDATE() {
        return STARTDATE;
    }

    public void setSTARTDATE(int STARTDATE) {
        this.STARTDATE = STARTDATE;
    }

    public int getENDDATE() {
        return ENDDATE;
    }

    public void setENDDATE(int ENDDATE) {
        this.ENDDATE = ENDDATE;
    }

    public String getCADNUM() {
        return CADNUM;
    }

    public void setCADNUM(String CADNUM) {
        this.CADNUM = CADNUM;
    }

    public String getROOMCADNUM() {
        return ROOMCADNUM;
    }

    public void setROOMCADNUM(String ROOMCADNUM) {
        this.ROOMCADNUM = ROOMCADNUM;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getOkato() {
        return okato;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    public String getOktmo() {
        return oktmo;
    }

    public void setOktmo(String oktmo) {
        this.oktmo = oktmo;
    }

    public String getIfnsfl() {
        return ifnsfl;
    }

    public void setIfnsfl(String ifnsfl) {
        this.ifnsfl = ifnsfl;
    }

    public String getIfnsul() {
        return ifnsul;
    }

    public void setIfnsul(String ifnsul) {
        this.ifnsul = ifnsul;
    }

    public String getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(String stateStatus) {
        this.stateStatus = stateStatus;
    }
}
