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
 * Сведения о помещениях
 */

@Entity
@Table(name = "room", indexes = {
        @Index(columnList = "houseguid", name = "houseguidroom"),
        @Index(columnList = "roomguid", name = "roomguid"),
        @Index(columnList = "postalcode", name = "postalcode")})
@Data
@EqualsAndHashCode(of = "ROOMID", callSuper = false)
public class Room extends AbstractFiasObject {

    public Room(){
    }

    public Room(String roomGuid, int endDate, int flatType, String flatNumber){
        this.ROOMGUID = roomGuid;
        this.ENDDATE = endDate;
        this.FLATTYPE = flatType;
        this.FLATNUMBER = flatNumber;
    }

    @Id
    @Column(name = "roomid", nullable = false, length = 32)
    private String ROOMID;

    //GUID
    @Column(name = "roomguid", nullable = false, length = 32)
    private String ROOMGUID;

    //Идентификатор родительского объекта (дома)
    @Column(name = "houseguid", length = 32)
    private String HOUSEGUID;

    //Признак действующего адресного объекта
    @Column(name = "livestatus", length = 1)
    private int LIVESTATUS;

    //Тип помещения
    @Column(name = "flattype", length = 1)
    private int FLATTYPE;

    //Номер помещения или офиса
    @Column(name = "flatnumber", length = 100)
    private String FLATNUMBER;

    //Почтовый индекс
    @Column(name = "postalcode", length = 6)
    private String POSTALCODE;

    //Кадастровый номер помещения
    @Column(name = "cadnum", length = 100)
    private String CADNUM;

    //Кадастровый номер комнаты в помещении
    @Column(name = "roomcadnum", length = 100)
    private String ROOMCADNUM;

    //Начало действия записи
    @Column(name = "startdate", length = 8)
    private int STARTDATE;

    //Окончание действия записи
    @Column(name = "enddate", length = 8)
    private int ENDDATE;

    @Transient
    private String IFNSFL;

    @Transient
    private String IFNSUL;

    @Transient
    private String OKATO;

    @Transient
    private String OKTMO;

    @Transient
    private String fullAddress;

    @Transient
    private String type;

    @Transient
    private String houseStateStatus;

    @Override
    public String getGuid(){
        return ROOMGUID;
    }
}
