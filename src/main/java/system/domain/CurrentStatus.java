package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по статусу актуальности
 */

@Entity
@Table(name = "current_status")
@Data
@EqualsAndHashCode(of = "CURENTSTID")
public class CurrentStatus {

    @Id
    @Column(name = "curentstid", nullable = false)
    private int CURENTSTID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
