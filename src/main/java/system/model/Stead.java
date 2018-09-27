package system.model;

import javax.persistence.*;

/**
 * Сведения о земельных участках
 */

@Entity
@Table(name = "stead", schema = "fias", indexes = {
        @Index(columnList = "parentguid", name = "parentguidstead")})
public class Stead {

//    @Column(name = "id", nullable = false)
//    private int id;

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

//    //Почтовый индекс
//    @Column(name = "postalcode")
//    private String POSTALCODE;

//    //Код региона
//    @Column(name = "regioncode")
//    private String REGIONCODE;

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

//    //Кадастровый номер
//    @Column(name = "cadnum")
//    private int CADNUM;
//
//    //Тип адресации
//    @Column(name = "divtype")
//    private int DIVTYPE;


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
}


