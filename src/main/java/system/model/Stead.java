package system.model;

import javax.persistence.*;

/**
 * Сведения о земельных участках
 */

@Entity
@Table(name = "stead", schema = "fias")
public class Stead {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "steadguid", nullable = false)
    private String STEADGUID;

    //Номер земельного участка
    @Column(name = "number")
    private String NUMBER;

    //Почтовый индекс
    @Column(name = "postalcode")
    private String POSTALCODE;

    //Код региона
    @Column(name = "regioncode")
    private String REGIONCODE;

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

    //Идентификатор объекта родительского объекта
    @Column(name = "parentguid")
    private String PARENTGUID;

//    //Уникальный идентификатор записи
//    @Column(name = "steadid", nullable = false)
//    private String STEADID;
//
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

    //Признак действующего адресного объекта
    @Column(name = "livestatus")
    private int LIVESTATUS;

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

    public String getSTEADGUID() {
        return STEADGUID;
    }

    public void setSTEADGUID(String STEADGUID) {
        this.STEADGUID = STEADGUID;
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

    public String getREGIONCODE() {
        return REGIONCODE;
    }

    public void setREGIONCODE(String REGIONCODE) {
        this.REGIONCODE = REGIONCODE;
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


