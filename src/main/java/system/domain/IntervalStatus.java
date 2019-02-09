package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по статусу интервалов домов
 */

@Entity
@Table(name = "interval_status")
@Data
@EqualsAndHashCode(of = "INTVSTATID")
public class IntervalStatus {

    @Id
    @Column(name = "intvstatid", nullable = false)
    private int INTVSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
