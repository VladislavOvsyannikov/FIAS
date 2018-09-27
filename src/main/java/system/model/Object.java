package system.model;

import javax.persistence.*;

/**
 * Информация классификатора адресообразующих элементов
 */

@Entity
@Table(name = "object", schema = "fias", indexes = {
        @Index(columnList = "parentguid", name = "parentguidobject")})
public class Object{

//    @Column(name = "id", nullable = false)
//    private int id;

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

    //Формализованное наименование
    @Column(name = "formalname")
    private String FORMALNAME;

    //Краткое наименование типа объекта
    @Column(name = "shortname", length = 50)
    private String SHORTNAME;

//    //Официальное наименование
//    @Column(name = "offname")
//    private String OFFNAME;

//    //Код региона
//    @Column(name = "regioncode")
//    private String REGIONCODE;
//
//    //Код автономии
//    @Column(name = "autocode")
//    private String AUTOCODE;

//    //Код района
//    @Column(name = "areacode")
//    private String AREACODE;

//    //Код города
//    @Column(name = "citycode")
//    private String CITYCODE;

//    //Код внутригородского района
//    @Column(name = "ctarcode")
//    private String CTARCODE;

//    //Код населенного пункта
//    @Column(name = "placecode")
//    private String PLACECODE;

//    //Код элемента планировочной структуры
//    @Column(name = "plancode")
//    private String PLANCODE;

//    //Код улицы
//    @Column(name = "streetcode")
//    private String STREETCODE;

//    //Код дополнительного адресообразующего элемента
//    @Column(name = "extrcode")
//    private String EXTRCODE;

//    //Код подчиненного дополнительного адресообразующего элемента
//    @Column(name = "sextcode")
//    private String SEXTCODE;

//    //Почтовый индекс
//    @Column(name = "postalcode")
//    private String POSTALCODE;

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

//    //Тип адресации
//    @Column(name = "divtype")
//    private int DIVTYPE;


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
}
