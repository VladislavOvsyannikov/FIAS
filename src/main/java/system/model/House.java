package system.model;

import javax.persistence.*;

/**
 * Информация по номерам домов улиц городов и населенных пунктов
 */

@Entity
@Table(name = "house", schema = "fias")
public class House {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    //Код региона
    @Column(name = "regioncode")
    private String REGIONCODE;

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

//    //Дата  внесения записи
//    @Column(name = "updatedate")
//    private String UPDATEDATE;

    //Номер дома
    @Column(name = "housenum")
    private String HOUSENUM;

    //Признак владения
    @Column(name = "eststatus")
    private int ESTSTATUS;

    //Номер корпуса
    @Column(name = "buildnum")
    private String BUILDNUM;

    //Номер строения
    @Column(name = "strucnum")
    private String STRUCNUM;

    //Признак строения
    @Column(name = "strstatus")
    private int STRSTATUS;

//    //Уникальный идентификатор записи дома
//    @Column(name = "houseid")
//    private String HOUSEID;

    //Глобальный уникальный идентификатор дома
    @Column(name = "houseguid", nullable = false)
    private String HOUSEGUID;

    //Guid записи родительского объекта (улицы, города, населенного пункта и т.п.)
    @Column(name = "aoguid", nullable = false)
    private String AOGUID;

//    //Начало действия записи
//    @Column(name = "startdate")
//    private String STARTDATE;
//
//    //Окончание действия записи
//    @Column(name = "enddate")
//    private String ENDDATE;

    //Состояние дома
    @Column(name = "statstatus", nullable = false)
    private int STATSTATUS;

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




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHOUSENUM() {
        return HOUSENUM;
    }

    public void setHOUSENUM(String HOUSENUM) {
        this.HOUSENUM = HOUSENUM;
    }

    public int getESTSTATUS() {
        return ESTSTATUS;
    }

    public void setESTSTATUS(int ESTSTATUS) {
        this.ESTSTATUS = ESTSTATUS;
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

    public int getSTRSTATUS() {
        return STRSTATUS;
    }

    public void setSTRSTATUS(int STRSTATUS) {
        this.STRSTATUS = STRSTATUS;
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
}
