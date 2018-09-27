package system.model;

import javax.persistence.*;

/**
 * Информация по номерам домов улиц городов и населенных пунктов
 */

@Entity
@Table(name = "house", schema = "fias", indexes = {
        @Index(columnList = "aoguid", name = "parentguidhouse")})
public class House {

//    @Column(name = "id", nullable = false)
//    private int id;

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

//    //Код региона
//    @Column(name = "regioncode")
//    private String REGIONCODE;
//
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

//    //Дата  внесения записи
//    @Column(name = "updatedate")
//    private String UPDATEDATE;

//    //Признак владения
//    @Column(name = "eststatus")
//    private int ESTSTATUS;

//    //Признак строения
//    @Column(name = "strstatus")
//    private int STRSTATUS;

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

//    //Счетчик записей домов для КЛАДР 4
//    @Column(name = "counter")
//    private int COUNTER;

//    //Кадастровый номер
//    @Column(name = "cadnum")
//    private int CADNUM;
//
//    //Тип адресации
//    @Column(name = "divtype")
//    private int DIVTYPE;


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

    public int getSTATSTATUS() {
        return STATSTATUS;
    }

    public void setSTATSTATUS(int STATSTATUS) {
        this.STATSTATUS = STATSTATUS;
    }
}
