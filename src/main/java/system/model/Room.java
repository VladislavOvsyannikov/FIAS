package system.model;

import javax.persistence.*;

/**
 * Сведения о помещениях
 */

@Entity
@Table(name = "room", schema = "fias", indexes = {
        @Index(columnList = "houseguid", name = "houseguidroom")})
public class Room {

//    @Id
//    @Column(name = "id", nullable = false)
//    private int id;

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

//    //Почтовый индекс
//    @Column(name = "postalcode")
//    private String POSTALCODE;

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
//    //Начало действия записи
//    @Column(name = "startdate")
//    private String STARTDATE;
//
//    //Окончание действия записи
//    @Column(name = "enddate")
//    private String ENDDATE;

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

    public String getFLATNUMBER() {
        return FLATNUMBER;
    }

    public void setFLATNUMBER(String FLATNUMBER) {
        this.FLATNUMBER = FLATNUMBER;
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
}
