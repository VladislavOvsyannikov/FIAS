package system.model;

import javax.persistence.*;

/**
 * Информация классификатора адресообразующих элементов
 */

@Entity
@Table(name = "object", schema = "fias")
public class Object{

    @Id
    @Column(name = "aoid", nullable = false)
    private String AOID;

//    @Id
//    @Column(name = "id", nullable = false)
//    private int id;

    @Column(name = "aoguid", nullable = false)
    private String AOGUID;

    //Формализованное наименование
    @Column(name = "formalname")
    private String FORMALNAME;

    //Код региона
    @Column(name = "regioncode")
    private String REGIONCODE;

    //Код автономии
    @Column(name = "autocode")
    private String AUTOCODE;

    //Код района
    @Column(name = "areacode")
    private String AREACODE;

    //Код города
    @Column(name = "citycode")
    private String CITYCODE;

    //Код внутригородского района
    @Column(name = "ctarcode")
    private String CTARCODE;

    //Код населенного пункта
    @Column(name = "placecode")
    private String PLACECODE;

    //Код элемента планировочной структуры
    @Column(name = "plancode")
    private String PLANCODE;

    //Код улицы
    @Column(name = "streetcode")
    private String STREETCODE;

    //Код дополнительного адресообразующего элемента
    @Column(name = "extrcode")
    private String EXTRCODE;

    //Код подчиненного дополнительного адресообразующего элемента
    @Column(name = "sextcode")
    private String SEXTCODE;

    //Официальное наименование
    @Column(name = "offname")
    private String OFFNAME;

    //Почтовый индекс
    @Column(name = "postalcode")
    private String POSTALCODE;

//    //Код ИФНС ФЛ
//    @Column(name = "ifnsfl")
//    private String IFNSFL;
//
//    //Код территориального участка ИФНС ФЛ
//    @Column(name = "terrifnsfl")
//    private String TERRIFNSFL;
//
//    //Код ИФНС ЮЛ
//    @Column(name = "ifnsul")
//    private String IFNSUL;
//
//    //Код территориального участка ИФНС ЮЛ
//    @Column(name = "terrifnsul")
//    private String TERRIFNSUL;
//
//    //OKATO
//    @Column(name = "okato")
//    private String OKATO;
//
//    //OKTMO
//    @Column(name = "oktmo")
//    private String OKTMO;

//    //Дата внесения записи
//    @Column(name = "updatedate")
//    private String UPDATEDATE;

    //Краткое наименование типа объекта
    @Column(name = "shortname")
    private String SHORTNAME;

    //Уровень адресного объекта
    @Column(name = "aolevel")
    private int AOLEVEL;

    //Идентификатор объекта родительского объекта
    @Column(name = "parentguid")
    private String PARENTGUID;

//    //Идентификатор записи связывания с предыдушей исторической записью
//    @Column(name = "previd")
//    private String PREVID;
//
//    //Идентификатор записи  связывания с последующей исторической записью
//    @Column(name = "nextid")
//    private String NEXTID;

//    //Код адресного объекта одной строкой с признаком актуальности из КЛАДР 4.0
//    @Column(name = "code")
//    private String CODE;
//
//    //Код адресного объекта из КЛАДР 4.0 одной строкой без признака актуальности (последних двух цифр)
//    @Column(name = "plaincode")
//    private String PLAINCODE;

//    //Статус актуальности адресного объекта
//    @Column(name = "actstatus")
//    private int ACTSTATUS;

//    //Статус центра
//    @Column(name = "centstatus")
//    private int CENTSTATUS;

//    //Причина появления записи
//    @Column(name = "operstatus")
//    private int OPERSTATUS;
//
//    //Статус актуальности КЛАДР 4
//    @Column(name = "currstatus")
//    private int CURRSTATUS;

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

    //Признак действующего адресного объекта
    @Column(name = "livestatus")
    private int LIVESTATUS;

//    //Тип адресации
//    @Column(name = "divtype")
//    private int DIVTYPE;


    public String getAoid() {
        return AOID;
    }

    public void setAoid(String AOID) {
        this.AOID = AOID;
    }

    public String getSHORTNAME() {
        return SHORTNAME;
    }

    public void setSHORTNAME(String SHORTNAME) {
        this.SHORTNAME = SHORTNAME;
    }

    public String getAOGUID() {
        return AOGUID;
    }

    public void setAOGUID(String AOGUID) {
        this.AOGUID = AOGUID;
    }

    public String getFORMALNAME() {
        return FORMALNAME;
    }

    public void setFORMALNAME(String FORMALNAME) {
        this.FORMALNAME = FORMALNAME;
    }

    public String getREGIONCODE() {
        return REGIONCODE;
    }

    public void setREGIONCODE(String REGIONCODE) {
        this.REGIONCODE = REGIONCODE;
    }

    public String getAUTOCODE() {
        return AUTOCODE;
    }

    public void setAUTOCODE(String AUTOCODE) {
        this.AUTOCODE = AUTOCODE;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getCITYCODE() {
        return CITYCODE;
    }

    public void setCITYCODE(String CITYCODE) {
        this.CITYCODE = CITYCODE;
    }

    public String getCTARCODE() {
        return CTARCODE;
    }

    public void setCTARCODE(String CTARCODE) {
        this.CTARCODE = CTARCODE;
    }

    public String getPLACECODE() {
        return PLACECODE;
    }

    public void setPLACECODE(String PLACECODE) {
        this.PLACECODE = PLACECODE;
    }

    public String getPLANCODE() {
        return PLANCODE;
    }

    public void setPLANCODE(String PLANCODE) {
        this.PLANCODE = PLANCODE;
    }

    public String getSTREETCODE() {
        return STREETCODE;
    }

    public void setSTREETCODE(String STREETCODE) {
        this.STREETCODE = STREETCODE;
    }

    public String getEXTRCODE() {
        return EXTRCODE;
    }

    public void setEXTRCODE(String EXTRCODE) {
        this.EXTRCODE = EXTRCODE;
    }

    public String getSEXTCODE() {
        return SEXTCODE;
    }

    public void setSEXTCODE(String SEXTCODE) {
        this.SEXTCODE = SEXTCODE;
    }

    public String getOFFNAME() {
        return OFFNAME;
    }

    public void setOFFNAME(String OFFNAME) {
        this.OFFNAME = OFFNAME;
    }

    public String getPOSTALCODE() {
        return POSTALCODE;
    }

    public void setPOSTALCODE(String POSTALCODE) {
        this.POSTALCODE = POSTALCODE;
    }

    public int getAOLEVEL() {
        return AOLEVEL;
    }

    public void setAOLEVEL(int AOLEVEL) {
        this.AOLEVEL = AOLEVEL;
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
}
