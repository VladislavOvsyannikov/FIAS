package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Информация по типам адресных объектов
 */

@Entity
@Table(name = "adress_object_type", indexes = {
        @Index(columnList = "addrlevel, scname", name = "AOT1")})
@Data
@EqualsAndHashCode(of = "KOD_T_ST")
public class AddressObjectType {

    @Id
    @Column(name = "kodtst", nullable = false)
    private String KOD_T_ST;

    //Уровень адресного объекта
    @Column(name = "addrlevel", nullable = false)
    private int LEVEL;

    //Полное наименование типа объекта
    @Column(name = "socrname", nullable = false)
    private String SOCRNAME;

    //Краткое наименование типа объекта
    @Column(name = "scname")
    private String SCNAME;
}
