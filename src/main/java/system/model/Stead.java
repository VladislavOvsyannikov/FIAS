package system.model;

import javax.persistence.*;

/**
 * Сведения о земельных участках
 */

@Entity
@Table(name = "stead", schema = "fias", indexes = {
        @Index(columnList = "parentguid", name = "parentguidstead")})
public class Stead {

    @Id
    @Column(name = "steadid", nullable = false, unique = true, length = 32)
    private String STEADID;

    @Column(name = "steadguid", nullable = false, length = 32)
    private String STEADGUID;

    //Идентификатор объекта родительского объекта
    @Column(name = "parentguid", length = 32)
    private String PARENTGUID;

    //Признак действующего адресного объекта
    @Column(name = "livestatus", length = 1)
    private int LIVESTATUS;

    //Номер земельного участка
    @Column(name = "number")
    private String NUMBER;

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

//    //Идентификатор записи связывания с предыдушей исторической записью
//    @Column(name = "previd")
//    private String PREVID;
//
//    //Идентификатор записи  связывания с последующей исторической записью
//    @Column(name = "nextid")
//    private String NEXTID;

//    //Причина появления записи
//    @Column(name = "operstatus")
//    private int OPERSTATUS;

//    //Внешний ключ на нормативный документ
//    @Column(name = "normdoc")
//    private String NORMDOC;

    public String getSTEADID() {
        return STEADID;
    }

    public void setSTEADID(String STEADID) {
        this.STEADID = STEADID;
    }

    public String getSTEADGUID() {
        return STEADGUID;
    }

    public void setSTEADGUID(String STEADGUID) {
        this.STEADGUID = STEADGUID;
    }

    public String getPARENTGUID() {
        return PARENTGUID;
    }

    public void setPARENTGUID(String PARENTGUID) {
        this.PARENTGUID = PARENTGUID;
    }

    public int getLIVESTATUS() {
        return LIVESTATUS;
    }

    public void setLIVESTATUS(int LIVESTATUS) {
        this.LIVESTATUS = LIVESTATUS;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
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


