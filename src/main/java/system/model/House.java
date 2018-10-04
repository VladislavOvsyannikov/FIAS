package system.model;

import javax.persistence.*;

/**
 * Информация по номерам домов улиц городов и населенных пунктов
 */

@Entity
@Table(name = "house", schema = "fias", indexes = {
        @Index(columnList = "aoguid", name = "parentguidhouse")})
public class House {

    @Id
    @Column(name = "houseid", nullable = false, unique = true, length = 32)
    private String HOUSEID;

    //Глобальный уникальный идентификатор дома
    @Column(name = "houseguid", nullable = false, length = 32)
    private String HOUSEGUID;

    //Guid записи родительского объекта (улицы, города, населенного пункта и т.п.)
    @Column(name = "aoguid", length = 32)
    private String AOGUID;

    //Состояние дома
    @Column(name = "statstatus", length = 2)
    private int STATSTATUS;

    //Номер дома
    @Column(name = "housenum", length = 50)
    private String HOUSENUM;

    //Номер корпуса
    @Column(name = "buildnum", length = 50)
    private String BUILDNUM;

    //Номер строения
    @Column(name = "strucnum", length = 50)
    private String STRUCNUM;

    //Почтовый индекс
    @Column(name = "postalcode", length = 6)
    private String POSTALCODE;

    //Код ИФНС ФЛ
    @Column(name = "ifnsfl", length = 4)
    private String IFNSFL;

    //Код ИФНС ЮЛ
    @Column(name = "ifnsul", length = 4)
    private String IFNSUL;

    //OKATO
    @Column(name = "okato", length = 11)
    private String OKATO;

    //OKTMO
    @Column(name = "oktmo", length = 11)
    private String OKTMO;

    //Начало действия записи
    @Column(name = "startdate", length = 10)
    private String STARTDATE;

    //Окончание действия записи
    @Column(name = "enddate", length = 10)
    private String ENDDATE;

    //Тип адресации
    @Column(name = "divtype", length = 1)
    private int DIVTYPE;

    //Кадастровый номер
    @Column(name = "cadnum", length = 100)
    private int CADNUM;

//    //Код региона
//    @Column(name = "regioncode")
//    private String REGIONCODE;
//
//    //Код территориального участка ИФНС ФЛ
//    @Column(name = "terrifnsfl")
//    private String TERRIFNSFL;
//
//    //Код территориального участка ИФНС ЮЛ
//    @Column(name = "terrifnsul")
//    private String TERRIFNSUL;

//    //Дата  внесения записи
//    @Column(name = "updatedate")
//    private String UPDATEDATE;

//    //Признак владения
//    @Column(name = "eststatus")
//    private int ESTSTATUS;

//    //Признак строения
//    @Column(name = "strstatus")
//    private int STRSTATUS;

//    //Внешний ключ на нормативный документ
//    @Column(name = "normdoc")
//    private String NORMDOC;

//    //Счетчик записей домов для КЛАДР 4
//    @Column(name = "counter")
//    private int COUNTER;

    public String getHOUSEID() {
        return HOUSEID;
    }

    public void setHOUSEID(String HOUSEID) {
        this.HOUSEID = HOUSEID;
    }

    public String getHOUSEGUID() {
        return HOUSEGUID;
    }

    public void setHOUSEGUID(String HOUSEGUID) {
        this.HOUSEGUID = HOUSEGUID;
    }

    public String getAOGUID() {
        return AOGUID;
    }

    public void setAOGUID(String AOGUID) {
        this.AOGUID = AOGUID;
    }

    public int getSTATSTATUS() {
        return STATSTATUS;
    }

    public void setSTATSTATUS(int STATSTATUS) {
        this.STATSTATUS = STATSTATUS;
    }

    public String getHOUSENUM() {
        return HOUSENUM;
    }

    public void setHOUSENUM(String HOUSENUM) {
        this.HOUSENUM = HOUSENUM;
    }

    public String getBUILDNUM() {
        return BUILDNUM;
    }

    public void setBUILDNUM(String BUILDNUM) {
        this.BUILDNUM = BUILDNUM;
    }

    public String getSTRUCNUM() {
        return STRUCNUM;
    }

    public void setSTRUCNUM(String STRUCNUM) {
        this.STRUCNUM = STRUCNUM;
    }

    public String getPOSTALCODE() {
        return POSTALCODE;
    }

    public void setPOSTALCODE(String POSTALCODE) {
        this.POSTALCODE = POSTALCODE;
    }

    public String getIFNSFL() {
        return IFNSFL;
    }

    public void setIFNSFL(String IFNSFL) {
        this.IFNSFL = IFNSFL;
    }

    public String getIFNSUL() {
        return IFNSUL;
    }

    public void setIFNSUL(String IFNSUL) {
        this.IFNSUL = IFNSUL;
    }

    public String getOKATO() {
        return OKATO;
    }

    public void setOKATO(String OKATO) {
        this.OKATO = OKATO;
    }

    public String getOKTMO() {
        return OKTMO;
    }

    public void setOKTMO(String OKTMO) {
        this.OKTMO = OKTMO;
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

    public int getDIVTYPE() {
        return DIVTYPE;
    }

    public void setDIVTYPE(int DIVTYPE) {
        this.DIVTYPE = DIVTYPE;
    }

    public int getCADNUM() {
        return CADNUM;
    }

    public void setCADNUM(int CADNUM) {
        this.CADNUM = CADNUM;
    }
}
