package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по статусу сооружения домов
 */

@Entity
@Table(name = "structure_status")
@Data
@EqualsAndHashCode(of = "STRSTATID")
public class StructureStatus {

    @Id
    @Column(name = "strstatid", nullable = false)
    private int STRSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
