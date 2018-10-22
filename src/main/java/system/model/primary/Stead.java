package system.model.primary;

import javax.persistence.*;

/**
 * Сведения о земельных участках
 */

@Entity
@Table(name = "stead", schema = "fias", indexes = {
        @Index(columnList = "parentguid", name = "parentguidstead"),
        @Index(columnList = "steadguid", name = "steadguid"),
        @Index(columnList = "postalcode", name = "postalcode")})
public class Stead {

    @Transient
    private String fullAddress;

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
    @Column(name = "startdate", length = 8)
    private int STARTDATE;

    //Окончание действия записи
    @Column(name = "enddate", length = 8)
    private int ENDDATE;

    //Кадастровый номер
    @Column(name = "cadnum", length = 100)
    private String CADNUM;

    //Внешний ключ на нормативный документ
    @Column(name = "normdoc", length = 32)
    private String NORMDOC;


//    //Тип адресации
//    @Column(name = "divtype", length = 1)
//    private int DIVTYPE;

//    //Идентификатор записи связывания с предыдушей исторической записью
//    @Column(name = "previd")
//    private String PREVID;
//
//    //Идентификатор записи  связывания с последующей исторической записью
//    @Column(name = "nextid")
//    private String NEXTID;

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

//    //Причина появления записи
//    @Column(name = "operstatus")
//    private int OPERSTATUS;


    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

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

    public String getNORMDOC() {
        return NORMDOC;
    }

    public void setNORMDOC(String NORMDOC) {
        this.NORMDOC = NORMDOC;
    }
}


