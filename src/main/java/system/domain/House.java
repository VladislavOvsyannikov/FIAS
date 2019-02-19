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
 * Информация о домах
 */

@Entity
@Table(name = "house", indexes = {
        @Index(columnList = "aoguid", name = "parentguidhouse"),
        @Index(columnList = "houseguid", name = "houseguid"),
        @Index(columnList = "postalcode", name = "postalcode")})
//        @Index(columnList = "aoguid", name = "houseIndex1"),
//        @Index(columnList = "houseguid", name = "houseIndex2"),
//        @Index(columnList = "postalcode", name = "houseIndex3"),
//        @Index(columnList = "ifnsfl", name = "houseIndex4"),
//        @Index(columnList = "ifnsul", name = "houseIndex5"),
//        @Index(columnList = "okato", name = "houseIndex6"),
//        @Index(columnList = "oktmo", name = "houseIndex7"),
//        @Index(columnList = "cadnum", name = "houseIndex8")})
@Data
@EqualsAndHashCode(of = "HOUSEID", callSuper = false)
public class House extends AbstractFiasObject {

    public House(){
    }

    public House(String houseGuid, int endDate, String houseNum, String buildNum, String sctucNum,
                 int estStatus, int strStatus){
        this.HOUSEGUID = houseGuid;
        this.ENDDATE = endDate;
        this.HOUSENUM = houseNum;
        this.BUILDNUM = buildNum;
        this.STRUCNUM = sctucNum;
        this.ESTSTATUS = estStatus;
        this.STRSTATUS = strStatus;
    }

    @Id
    @Column(name = "houseid", nullable = false, length = 32)
    private String HOUSEID;

    //GUID
    @Column(name = "houseguid", nullable = false, length = 32)
    private String HOUSEGUID;

    //Guid записи родительского объекта
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

    //Признак владения
    @Column(name = "eststatus", length = 2)
    private int ESTSTATUS;

    //Признак строения
    @Column(name = "strstatus", length = 2)
    private int STRSTATUS;

    //Внешний ключ на нормативный документ
    @Column(name = "normdoc", length = 32)
    private String NORMDOC;

    @Transient
    private String fullAddress;

    @Transient
    private String type;

    @Transient
    private String name;

    @Transient
    private String houseStateStatus;

    @Override
    public String getGuid(){
        return HOUSEGUID;
    }
}
