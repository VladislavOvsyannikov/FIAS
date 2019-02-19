package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Сведения о земельных участках
 */

@Entity
@Table(name = "stead", indexes = {
        @Index(columnList = "parentguid", name = "parentguidstead"),
        @Index(columnList = "steadguid", name = "steadguid"),
        @Index(columnList = "postalcode", name = "postalcode")})
//        @Index(columnList = "parentguid", name = "steadIndex1"),
//        @Index(columnList = "steadguid", name = "steadIndex2"),
//        @Index(columnList = "postalcode", name = "steadIndex3"),
//        @Index(columnList = "ifnsfl", name = "steadIndex4"),
//        @Index(columnList = "ifnsul", name = "steadIndex5"),
//        @Index(columnList = "okato", name = "steadIndex6"),
//        @Index(columnList = "oktmo", name = "steadIndex7"),
//        @Index(columnList = "cadnum", name = "steadIndex8")})
@Data
@EqualsAndHashCode(of = "STEADID", callSuper = false)
public class Stead extends AbstractFiasObject {

    public Stead() {
    }

    public Stead(String steadGuid, String number, int endDate) {
        this.STEADGUID = steadGuid;
        this.NUMBER = number;
        this.ENDDATE = endDate;
    }

    @Id
    @Column(name = "steadid", nullable = false, length = 32)
    private String STEADID;

    //GUID
    @Column(name = "steadguid", nullable = false, length = 32)
    private String STEADGUID;

    //Идентификатор объекта родительского объекта
    @Column(name = "parentguid", length = 32)
    private String PARENTGUID;

    //Признак действующего адресного объекта
    @Column(name = "livestatus", length = 1)
    private int LIVESTATUS;

    //Номер земельного участка
    @Column(name = "steadnumber")
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

    @Transient
    private String fullAddress;

    @Override
    public String getGuid() {
        return STEADGUID;
    }
}


