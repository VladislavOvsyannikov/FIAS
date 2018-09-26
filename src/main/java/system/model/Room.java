package system.model;

import javax.persistence.*;

/**
 * Сведения о помещениях
 */

@Entity
@Table(name = "room", schema = "fias")
public class Room {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

//    //Уникальный идентификатор записи
//    @Column(name = "roomid", nullable = false)
//    private String ROOMID;

    @Column(name = "roomguid", nullable = false)
    private String ROOMGUID;

    //Номер помещения или офиса
    @Column(name = "flatnumber")
    private String FLATNUMBER;

    //Тип помещения
    @Column(name = "flattype")
    private int FLATTYPE;

    //Тип комнаты
    @Column(name = "roomtype")
    private int ROOMTYPE;

    //Код региона
    @Column(name = "regioncode")
    private String REGIONCODE;

    //Почтовый индекс
    @Column(name = "postalcode")
    private String POSTALCODE;

//    //Дата внесения записи
//    @Column(name = "updatedate")
//    private String UPDATEDATE;

    //Идентификатор родительского объекта (дома)
    @Column(name = "houseguid")
    private String HOUSEGUID;

//    //Идентификатор записи связывания с предыдушей исторической записью
//    @Column(name = "previd")
//    private String PREVID;
//
//    //Идентификатор записи  связывания с последующей исторической записью
//    @Column(name = "nextid")
//    private String NEXTID;
//
//    //Начало действия записи
//    @Column(name = "startdate")
//    private String STARTDATE;
//
//    //Окончание действия записи
//    @Column(name = "enddate")
//    private String ENDDATE;

    //Признак действующего адресного объекта
    @Column(name = "livestatus")
    private int LIVESTATUS;

//    //Внешний ключ на нормативный документ
//    @Column(name = "normdoc")
//    private String NORMDOC;
//
//    //Причина появления записи
//    @Column(name = "operstatus")
//    private int OPERSTATUS;
//
//    //Кадастровый номер помещения
//    @Column(name = "cadnum")
//    private String CADNUM;
//
//    //Кадастровый номер комнаты в помещении
//    @Column(name = "roomcadnum")
//    private String ROOMCADNUM;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getROOMTYPE() {
        return ROOMTYPE;
    }

    public void setROOMTYPE(int ROOMTYPE) {
        this.ROOMTYPE = ROOMTYPE;
    }

    public String getREGIONCODE() {
        return REGIONCODE;
    }

    public void setREGIONCODE(String REGIONCODE) {
        this.REGIONCODE = REGIONCODE;
    }

    public String getPOSTALCODE() {
        return POSTALCODE;
    }

    public void setPOSTALCODE(String POSTALCODE) {
        this.POSTALCODE = POSTALCODE;
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
}
