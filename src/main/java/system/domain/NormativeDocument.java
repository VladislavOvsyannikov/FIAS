package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Информация по сведениям по нормативным документам,
 * являющимся основанием присвоения адресному элементу наименования
 */

@Entity
@Table(name = "normative_document")
@Data
@EqualsAndHashCode(of = "NORMDOCID")
public class NormativeDocument {

    @Id
    @Column(name = "normdocid", nullable = false, length = 32)
    private String NORMDOCID;

    //Тип документа
    @Column(name = "doctype", length = 3)
    private int DOCTYPE;

    @Transient
    private String type;
}
