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
 * Информация классификатора адресообразующих элементов
 */

@Entity
@Table(name = "addr_object", indexes = {
        @Index(columnList = "parentguid", name = "parentguidobject"),
        @Index(columnList = "aoguid", name = "aoguid"),
        @Index(columnList = "postalcode", name = "postalcode"),
        @Index(columnList = "formalname", name = "formalname")})
@Data
@EqualsAndHashCode(of = "AOID", callSuper = false)
public class AddrObject extends AbstractFiasObject {

    public AddrObject(){
    }

    public AddrObject(String aoGuid, String formalName, String shortName, int aoLevel, int endDate){
        this.AOGUID = aoGuid;
        this.FORMALNAME = formalName;
        this.SHORTNAME = shortName;
        this.AOLEVEL = aoLevel;
        this.ENDDATE = endDate;
    }

    public AddrObject(String aoGuid, String parentGuid, String postalCode, String formalName, String shortName,
                      int aoLevel, int endDate){
        this.AOGUID = aoGuid;
        this.PARENTGUID = parentGuid;
        this.POSTALCODE = postalCode;
        this.FORMALNAME = formalName;
        this.SHORTNAME = shortName;
        this.AOLEVEL = aoLevel;
        this.ENDDATE = endDate;
    }

    @Id
    @Column(name = "aoid", nullable = false, length = 32)
    private String AOID;

    //GUID
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

    @Transient
    private String fullAddress;

    @Override
    public String getGuid(){
        return AOGUID;
    }
}
