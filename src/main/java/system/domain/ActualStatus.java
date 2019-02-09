package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Статус актуальности
 */

@Entity
@Table(name = "actual_status")
@Data
@EqualsAndHashCode(of = "ACTSTATID")
public class ActualStatus {

    @Id
    @Column(name = "actstatid", nullable = false)
    private int ACTSTATID;

    //Актуальность
    @Column(name = "name", nullable = false)
    private String NAME;
}
