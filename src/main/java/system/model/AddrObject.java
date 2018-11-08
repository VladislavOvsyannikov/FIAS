package system.model;

import javax.persistence.*;

/**
 * Информация классификатора адресообразующих элементов
 */

@Entity
@Table(name = "object", schema = "fias", indexes = {
        @Index(columnList = "parentguid", name = "parentguidobject"),
        @Index(columnList = "aoguid", name = "aoguid"),
        @Index(columnList = "postalcode", name = "postalcode")})
public class AddrObject {

    @Transient
    private String fullAddress;

    @Id
    @Column(name = "aoid", nullable = false, unique = true, length = 32)
    private String AOID;

    @Column(name = "aoguid", nullable = false, length = 32)
    private String AOGUID;

    //Идентификатор объекта родительского объекта
    @Column(name = "parentguid", length = 32)
    private String PARENTGUID;

    //Уровень адресного объекта
    @Column(name = "aolevel", length = 2)
    private int AOLEVEL;

    //Признак действующего адресного объекта
    @Column(name = "livestatus", length = 1)
    private int LIVESTATUS;

    //Статус актуальности адресного объекта
    @Column(name = "actstatus", length = 1)
    private int ACTSTATUS;

    //Формализованное наименование
    @Column(name = "formalname")
    private String FORMALNAME;

    //Краткое наименование типа объекта
    @Column(name = "shortname", length = 50)
    private String SHORTNAME;

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

    //Внешний ключ на нормативный документ
    @Column(name = "normdoc", length = 32)
    private String NORMDOC;

    //Код адресного объекта одной строкой с признаком актуальности из КЛАДР 4.0
    @Column(name = "code", length = 17)
    private String CODE;


//    //Тип адресации
//    @Column(name = "divtype", length = 1)
//    private int DIVTYPE;

//    //Код региона
//    @Column(name = "regioncode", length = 2)
//    private String REGIONCODE;
//
//    //Код автономии
//    @Column(name = "autocode", length = 1)
//    private String AUTOCODE;
//
//    //Код района
//    @Column(name = "areacode", length = 3)
//    private String AREACODE;

//    //Код города
//    @Column(name = "citycode", length = 3)
//    private String CITYCODE;
//
//    //Код внутригородского района
//    @Column(name = "ctarcode", length = 3)
//    private String CTARCODE;
//
//    //Код населенного пункта
//    @Column(name = "placecode", length = 3)
//    private String PLACECODE;
//
//    //Код элемента планировочной структуры
//    @Column(name = "plancode", length = 4)
//    private String PLANCODE;
//
//    //Код улицы
//    @Column(name = "streetcode", length = 4)
//    private String STREETCODE;

//    //Код дополнительного адресообразующего элемента
//    @Column(name = "extrcode", length = 4)
//    private String EXTRCODE;
//
//    //Код подчиненного дополнительного адресообразующего элемента
//    @Column(name = "sextcode", length = 3)
//    private String SEXTCODE;

//    //Код территориального участка ИФНС ФЛ
//    @Column(name = "terrifnsfl", length = 4)
//    private String TERRIFNSFL;

//    //Код территориального участка ИФНС ЮЛ
//    @Column(name = "terrifnsul", length = 4)
//    private String TERRIFNSUL;

//    //Дата внесения записи
//    @Column(name = "updatedate", length = 10)
//    private String UPDATEDATE;
//
//    //Идентификатор записи связывания с предыдушей исторической записью
//    @Column(name = "previd", length = 32)
//    private String PREVID;
//
//    //Идентификатор записи  связывания с последующей исторической записью
//    @Column(name = "nextid", length = 32)
//    private String NEXTID;

//    //Статус центра
//    @Column(name = "centstatus", length = 1)
//    private int CENTSTATUS;

//    //Причина появления записи
//    @Column(name = "operstatus", length = 2)
//    private int OPERSTATUS;

//    //Официальное наименование
//    @Column(name = "offname")
//    private String OFFNAME;
//
//    //Код адресного объекта из КЛАДР 4.0 одной строкой без признака актуальности (последних двух цифр)
//    @Column(name = "plaincode", length = 15)
//    private String PLAINCODE;

//    //Статус актуальности КЛАДР 4
//    @Column(name = "currstatus")
//    private int CURRSTATUS;


    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getAOID() {
        return AOID;
    }

    public void setAOID(String AOID) {
        this.AOID = AOID;
    }

    public String getAOGUID() {
        return AOGUID;
    }

    public void setAOGUID(String AOGUID) {
        this.AOGUID = AOGUID;
    }

    public String getPARENTGUID() {
        return PARENTGUID;
    }

    public void setPARENTGUID(String PARENTGUID) {
        this.PARENTGUID = PARENTGUID;
    }

    public int getAOLEVEL() {
        return AOLEVEL;
    }

    public void setAOLEVEL(int AOLEVEL) {
        this.AOLEVEL = AOLEVEL;
    }

    public int getLIVESTATUS() {
        return LIVESTATUS;
    }

    public void setLIVESTATUS(int LIVESTATUS) {
        this.LIVESTATUS = LIVESTATUS;
    }

    public int getACTSTATUS() {
        return ACTSTATUS;
    }

    public void setACTSTATUS(int ACTSTATUS) {
        this.ACTSTATUS = ACTSTATUS;
    }

    public String getFORMALNAME() {
        return FORMALNAME;
    }

    public void setFORMALNAME(String FORMALNAME) {
        this.FORMALNAME = FORMALNAME;
    }

    public String getSHORTNAME() {
        return SHORTNAME;
    }

    public void setSHORTNAME(String SHORTNAME) {
        this.SHORTNAME = SHORTNAME;
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

    public String getNORMDOC() {
        return NORMDOC;
    }

    public void setNORMDOC(String NORMDOC) {
        this.NORMDOC = NORMDOC;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }
}
